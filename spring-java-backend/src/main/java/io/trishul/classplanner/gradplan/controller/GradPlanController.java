package io.trishul.classplanner.gradplan.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.trishul.classplanner.auth.SessionManager;
import io.trishul.classplanner.gradplan.controller.dto.GetGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.mapper.GradPlanMapper;
import io.trishul.classplanner.gradplan.model.GradPlan;
import io.trishul.classplanner.gradplan.repository.GradPlanRepository;
import io.trishul.classplanner.gradplan.service.GradPlanAIResponseProcessor;
import io.trishul.classplanner.gradplan.service.GradPlanAIService;
import io.trishul.classplanner.gradplan.service.PDFProcessingService;
import io.trishul.classplanner.user.model.User;

@RestController
@RequestMapping("/api/v1/gradplans")
public class GradPlanController {
  @Autowired
  private GradPlanRepository repository;

  @Autowired
  private GradPlanMapper mapper;

  @Autowired
  private SessionManager sessionManager;

  @Autowired
  private PDFProcessingService pdfProcessingService;

  @Autowired
  private GradPlanAIService aiService;

  @Autowired
  private GradPlanAIResponseProcessor aiResponseProcessor;

  @GetMapping
  public List<GetGradPlanDTO> getPlans(@RequestParam(required = false) String fileName,
      @RequestParam(required = false) String programName,
      @RequestParam(required = false) String majorName,
      @RequestParam(required = false) Long creditsCompleted,
      @RequestParam(required = false) Long creditsRequired,
      @RequestParam(required = false) Double cgpa,
      @RequestParam(required = false) String programLevel) {

    GradPlan probe = new GradPlan();
    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    probe.setUser(user);

    probe.setFileName(fileName);
    probe.setProgramName(programName);
    probe.setMajorName(majorName);
    probe.setCreditsCompleted(creditsCompleted);
    probe.setCreditsRequired(creditsRequired);
    probe.setCgpa(cgpa);
    probe.setProgramLevel(programLevel);

    ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

    return repository.findAll(Example.of(probe, matcher)).stream().map(mapper::toGetDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetGradPlanDTO> getPlan(@PathVariable Long id) {
    return repository.findById(id).map(mapper::toGetDTO).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Transactional
  public GetGradPlanDTO createPlan(@RequestPart("file-name") String fileName,
      @RequestPart("file") MultipartFile file) throws IOException {
    GradPlan plan = new GradPlan();
    plan.setFileName(fileName);

    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    plan.setUser(user);

    String base64Content = pdfProcessingService.convertPDFToBase64Image(file);
    plan.setPdfContentBase64(base64Content);

    // Process with AI
    String aiResponse = aiService.processImageContent(base64Content);
    aiResponseProcessor.updateGradPlanFromAIResponse(plan, aiResponse);

    GradPlan persisted = repository.save(plan);
    repository.flush();
    return mapper.toGetDTO(persisted);
  }

  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> deletePlans(@RequestParam List<Long> ids) {
    GradPlan probe = new GradPlan();
    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    probe.setUser(user);

    repository.softDelete(ids, Example.of(probe));
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/archived")
  public List<GetGradPlanDTO> getArchivedPlans() {
    GradPlan probe = new GradPlan();
    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    probe.setUser(user);

    return repository.findAllArchived(Example.of(probe)).stream().map(mapper::toGetDTO)
        .collect(Collectors.toList());
  }

  @PutMapping("/archived")
  @Transactional
  public ResponseEntity<Void> activatePlans(@RequestParam List<Long> ids) {
    GradPlan probe = new GradPlan();
    User user = new User();
    user.setId(sessionManager.getCurrentUserId());
    probe.setUser(user);

    repository.restore(ids, Example.of(probe));
    return ResponseEntity.noContent().build();
  }
}
