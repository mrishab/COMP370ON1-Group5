package io.trishul.classplanner.classplan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import io.trishul.classplanner.classplan.controller.dto.GetClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PostClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PutClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.mapper.ClassPlanMapper;
import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.classplan.repository.ClassPlanRepository;
import io.trishul.classplanner.gradplan.model.GradPlan;

@RestController
@RequestMapping("/api/v1/classplans")
public class ClassPlanController {
    @Autowired
    private ClassPlanRepository repository;

    @Autowired
    private ClassPlanMapper mapper;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping
    public List<GetClassPlanDTO> getPlans() {
        ClassPlan probe = new ClassPlan();
        GradPlan gradPlan = new GradPlan();
        gradPlan.setId(sessionManager.getCurrentUserId());
        probe.setGradPlan(gradPlan);

        return repository.findAll(Example.of(probe))
                .stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClassPlanDTO> getPlan(@PathVariable Long id) {
        ClassPlan probe = new ClassPlan();
        probe.setId(id);
        GradPlan gradPlan = new GradPlan();
        gradPlan.setId(sessionManager.getCurrentUserId());
        probe.setGradPlan(gradPlan);

        return repository.findOne(Example.of(probe))
                .map(mapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public GetClassPlanDTO createPlan(@RequestBody PostClassPlanDTO dto) {
        return mapper.toGetDTO(repository.save(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetClassPlanDTO> updatePlan(@PathVariable Long id, @RequestBody PutClassPlanDTO dto) {
        ClassPlan probe = new ClassPlan();
        probe.setId(id);
        GradPlan gradPlan = new GradPlan();
        gradPlan.setId(sessionManager.getCurrentUserId());
        probe.setGradPlan(gradPlan);

        return repository.findOne(Example.of(probe))
                .map(plan -> {
                    mapper.updateEntity(plan, dto);
                    return mapper.toGetDTO(repository.save(plan));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

        return repository.findAllArchived(Example.of(probe))
                .stream()
                .filter(plan -> plan.getGradPlan().getId().equals(sessionManager.getCurrentUserId()))
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
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