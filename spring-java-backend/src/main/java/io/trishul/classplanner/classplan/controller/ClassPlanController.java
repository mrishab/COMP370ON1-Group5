package io.trishul.classplanner.classplan.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.trishul.classplanner.auth.SessionManager;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.classplan.controller.dto.GetClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PostClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.mapper.ClassPlanMapper;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.classplan.repository.ClassPlanRepository;
import io.trishul.classplanner.classplan.service.ClassPlanAIResponseProcessor;
import io.trishul.classplanner.classplan.service.ClassPlanAIService;
import io.trishul.classplanner.gradplan.model.GradPlan;
import io.trishul.classplanner.gradplan.repository.GradPlanRepository;
import io.trishul.classplanner.user.model.User;

@RestController
@RequestMapping("/api/v1/classplans")
public class ClassPlanController {
  @Autowired
  private ClassPlanRepository repository;

  @Autowired
  private GradPlanRepository gradPlanRepository;

  @Autowired
  private ClassPlanMapper mapper;

  @Autowired
  private SessionManager sessionManager;

  @Autowired
  private ClassPlanAIService aiService;

  @Autowired
  private ClassPlanAIResponseProcessor aiResponseProcessor;

  @GetMapping
  public List<GetClassPlanDTO> getPlans(@RequestParam(required = false) String description,
      @RequestParam(required = false) ClassDistribution classDistribution,
      @RequestParam(required = false) BurdenCapacity burdenCapacity) {

    User user = new User();
    user.setId(sessionManager.getCurrentUserId());

    ClassPlan probe = new ClassPlan();
    GradPlan gradPlan = new GradPlan();

    gradPlan.setUser(user);
    probe.setGradPlan(gradPlan);
    probe.setDescription(description);
    probe.setClassDistribution(classDistribution);
    probe.setBurdenCapacity(burdenCapacity);

    ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

    return repository.findAll(Example.of(probe, matcher)).stream().map(mapper::toGetDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetClassPlanDTO> getPlan(@PathVariable Long id) {
    return repository.findById(id).map(mapper::toGetDTO).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @Transactional
  public GetClassPlanDTO createPlan(@RequestBody PostClassPlanDTO dto) {
    ClassPlan classPlan = mapper.toEntity(dto);

    // Find the associated grad plan
    GradPlan probe = new GradPlan();
    probe.setId(dto.getGradPlanId());
    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    probe.setUser(user);

    GradPlan gradPlan = gradPlanRepository.findOne(Example.of(probe))
        .orElseThrow(() -> new RuntimeException("Grad plan not found"));

    // Generate class plan using AI
    String aiResponse = aiService.generateClassPlan(classPlan, gradPlan.getDetails());

    // Process AI response and update class plan
    aiResponseProcessor.updateClassPlanFromAIResponse(classPlan, aiResponse);

    ClassPlan persisted = repository.save(classPlan);
    repository.flush();
    return mapper.toGetDTO(persisted);
  }

  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> deletePlans(@RequestParam List<Long> ids) {
    ClassPlan probe = new ClassPlan();
    GradPlan gradPlan = new GradPlan();
    gradPlan.setId(sessionManager.getCurrentUserId());
    probe.setGradPlan(gradPlan);

    repository.softDelete(ids, Example.of(probe));
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/archived")
  public List<GetClassPlanDTO> getArchivedPlans() {
    ClassPlan probe = new ClassPlan();
    GradPlan gradPlan = new GradPlan();
    gradPlan.setArchived(true);
    gradPlan.setId(sessionManager.getCurrentUserId());
    probe.setGradPlan(gradPlan);

    return repository.findAllArchived(Example.of(probe)).stream()
        .filter(plan -> plan.getGradPlan().getId().equals(sessionManager.getCurrentUserId()))
        .map(mapper::toGetDTO).collect(Collectors.toList());
  }

  @PutMapping("/archived")
  @Transactional
  public ResponseEntity<Void> activatePlans(@RequestParam List<Long> ids) {
    ClassPlan probe = new ClassPlan();
    GradPlan gradPlan = new GradPlan();
    gradPlan.setId(sessionManager.getCurrentUserId());
    probe.setGradPlan(gradPlan);

    repository.restore(ids, Example.of(probe));
    return ResponseEntity.noContent().build();
  }
}
