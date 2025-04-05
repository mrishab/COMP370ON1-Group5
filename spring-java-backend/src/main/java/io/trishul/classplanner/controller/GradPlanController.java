package io.trishul.classplanner.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.trishul.classplanner.model.GradPlan;
import io.trishul.classplanner.repository.GradPlanRepository;

@RestController
@RequestMapping("/api/v1/gradplans")
public class GradPlanController {
    
    @Autowired 
    private GradPlanRepository gradPlanRepository;

    @GetMapping
    public List<GradPlan> getPlans() {
        return gradPlanRepository.findByUserId(UserContext.getCurrentUser());
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
        gradPlanRepository.deleteByIdAndUserId(id, UserContext.getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}