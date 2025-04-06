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

import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.classplan.repository.ClassPlanRepository;
import io.trishul.classplanner.config.UserContext;

@RestController
@RequestMapping("/api/v1/classplans")
public class ClassPlanController {

    @Autowired
    private ClassPlanRepository classPlanRepository;

    @GetMapping
    public List<ClassPlan> getPlans(@RequestParam(required = false) List<Long> ids) {
        ClassPlan probe = new ClassPlan();
        probe.setUserId(UserContext.getCurrentUser());
        
        if (ids != null) {
            return classPlanRepository.findAll(Example.of(probe))
                .stream()
                .filter(plan -> ids.contains(plan.getId()))
                .collect(Collectors.toList());
        }
        return classPlanRepository.findAll(Example.of(probe));
    }

    @PostMapping
    public ClassPlan createPlan(@RequestBody ClassPlan plan) {
        plan.setUserId(UserContext.getCurrentUser());
        return classPlanRepository.save(plan);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClassPlan> updatePlan(@PathVariable Long id, @RequestBody ClassPlan plan) {
        ClassPlan probe = new ClassPlan();
        probe.setId(id);
        probe.setUserId(UserContext.getCurrentUser());
        
        return classPlanRepository.findOne(Example.of(probe))
            .map(existing -> {
                plan.setId(id);
                plan.setUserId(UserContext.getCurrentUser());
                return ResponseEntity.ok(classPlanRepository.save(plan));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        ClassPlan probe = new ClassPlan();
        probe.setId(id);
        probe.setUserId(UserContext.getCurrentUser());
        
        classPlanRepository.findOne(Example.of(probe))
            .ifPresent(plan -> classPlanRepository.delete(plan));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deletePlans(@RequestBody List<Long> ids) {
        ClassPlan probe = new ClassPlan();
        probe.setUserId(UserContext.getCurrentUser());
        
        List<ClassPlan> toDelete = classPlanRepository.findAll(Example.of(probe))
            .stream()
            .filter(plan -> ids.contains(plan.getId()))
            .collect(Collectors.toList());
        classPlanRepository.deleteAll(toDelete);
        return ResponseEntity.noContent().build();
    }
}