package io.trishul.classplanner.gradplan.controller;

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
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.auth.SessionManager;
import io.trishul.classplanner.gradplan.controller.dto.GetGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.PostGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.PutGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.mapper.GradPlanMapper;
import io.trishul.classplanner.gradplan.model.GradPlan;
import io.trishul.classplanner.gradplan.repository.GradPlanRepository;
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

    @GetMapping
    public List<GetGradPlanDTO> getPlans() {
        GradPlan probe = new GradPlan();
        probe.setUser(User.builder().id(sessionManager.getCurrentUserId()).build());

        return repository.findAll(Example.of(probe))
            .stream()
            .map(mapper::toGetDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGradPlanDTO> getPlan(@PathVariable Long id) {
        GradPlan probe = new GradPlan();
        probe.setId(id);
        probe.setUser(User.builder().id(sessionManager.getCurrentUserId()).build());

        return repository.findOne(Example.of(probe))
            .map(mapper::toGetDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public GetGradPlanDTO createPlan(@RequestBody PostGradPlanDTO dto) {
        GradPlan plan = mapper.toEntity(dto);
        plan.setUser(User.builder().id(sessionManager.getCurrentUserId()).build());
        return mapper.toGetDTO(repository.save(plan));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetGradPlanDTO> updatePlan(@PathVariable Long id, @RequestBody PutGradPlanDTO dto) {
        GradPlan probe = new GradPlan();
        probe.setId(id);
        probe.setUser(User.builder().id(sessionManager.getCurrentUserId()).build());

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
    public ResponseEntity<Void> deletePlans(@RequestBody List<Long> ids) {
        GradPlan probe = new GradPlan();
        probe.setUser(User.builder().id(sessionManager.getCurrentUserId()).build());

        List<Long> toDelete = repository.findAll(Example.of(probe))
            .stream()
            .filter(plan -> ids.contains(plan.getId()))
            .map(GradPlan::getId)
            .collect(Collectors.toList());

        repository.softDelete(toDelete);
        return ResponseEntity.noContent().build();
    }
}