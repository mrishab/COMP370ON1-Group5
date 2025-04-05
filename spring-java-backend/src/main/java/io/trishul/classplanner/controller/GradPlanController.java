package io.trishul.classplanner.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.config.UserContext;
import io.trishul.classplanner.model.GradPlan;
import io.trishul.classplanner.service.InMemoryStorageService;

@RestController
@RequestMapping("/api/v1/gradplans")
public class GradPlanController {
    @Autowired InMemoryStorageService storage;

    @GetMapping
    public List<GradPlan> getPlans() {
        return storage.getGradPlansByUser(UserContext.getCurrentUser());
    }

    @PostMapping
    public GradPlan create(@RequestBody Map<String, String> body) {
        return storage.saveGradPlan(UserContext.getCurrentUser(), body.get("fileName"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        storage.deleteGradPlan(id, UserContext.getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}