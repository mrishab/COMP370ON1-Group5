package io.trishul.classplanner.controller;

import io.trishul.classplanner.model.GradPlan;
import io.trishul.classplanner.service.InMemoryStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gradplans")
public class GradPlanController {
    @Autowired InMemoryStorageService storage;

    @GetMapping
    public List<GradPlan> getPlans(@RequestHeader("X-USER-ID") String userId) {
        return storage.getGradPlansByUser(userId);
    }

    @PostMapping
    public GradPlan create(@RequestHeader("X-USER-ID") String userId, @RequestBody Map<String, String> body) {
        return storage.saveGradPlan(userId, body.get("fileName"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("X-USER-ID") String userId, @PathVariable Long id) {
        storage.deleteGradPlan(id, userId);
        return ResponseEntity.noContent().build();
    }
}