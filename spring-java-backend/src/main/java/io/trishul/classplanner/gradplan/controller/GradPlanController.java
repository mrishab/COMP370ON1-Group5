package io.trishul.classplanner.gradplan.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.config.UserContext;
import io.trishul.classplanner.gradplan.repository.GradPlanRepository;
import io.trishul.classplanner.model.GradPlan;

@RestController
@RequestMapping("/api/v1/gradplans")
public class GradPlanController {
    
    @Autowired 
    private GradPlanRepository gradPlanRepository;

    @GetMapping
    public List<GradPlan> getPlans() {
        GradPlan probe = new GradPlan();
        probe.setUserId(UserContext.getCurrentUser());
        return gradPlanRepository.findAll(Example.of(probe));
    }

    @PostMapping
    public GradPlan create(@RequestBody Map<String, String> body) {
        GradPlan plan = new GradPlan();
        plan.setUserId(UserContext.getCurrentUser());
        plan.setFileName(body.get("fileName"));
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());
        return gradPlanRepository.save(plan);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        GradPlan probe = new GradPlan();
        probe.setId(id);
        probe.setUserId(UserContext.getCurrentUser());
        
        gradPlanRepository.findOne(Example.of(probe))
            .ifPresent(plan -> gradPlanRepository.delete(plan));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deletePlans(@RequestBody List<Long> ids) {
        GradPlan probe = new GradPlan();
        probe.setUserId(UserContext.getCurrentUser());
        
        List<GradPlan> toDelete = gradPlanRepository.findAll(Example.of(probe))
            .stream()
            .filter(plan -> ids.contains(plan.getId()))
            .collect(Collectors.toList());
        gradPlanRepository.deleteAll(toDelete);
        return ResponseEntity.noContent().build();
    }
}