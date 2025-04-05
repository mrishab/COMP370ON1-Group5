package io.trishul.classplanner.classplan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.trishul.classplanner.config.UserContext;lanResponse;
import io.trishul.classplanner.config.UserContext;
import io.trishul.classplanner.model.ClassPlan;

@RestController
@RequestMapping("/api/v1/classplans")
public class ClassPlanController {

    @Autowired
    private ClassPlanRepository classPlanRepository;

    @PostMapping
    public ResponseEntity<ClassPlanResponse> generateClassPlan(@RequestBody ClassPlanRequest request) {
        List<String> courses = new ArrayList<>();
        courses.add("COMP 101");
        courses.add("COMP 102");
        courses.add("MATH 201");

        ClassPlanResponse response = new ClassPlanResponse();
        response.setPlanName(request.getMajor() + " Year " + request.getYear());
        response.setCourses(courses);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<ClassPlan> getPlans(@RequestParam(required = false) List<Long> ids) {
        if (ids == null) {
            return classPlanRepository.findByUserId(UserContext.getCurrentUser());
        }
        return classPlanRepository.findByUserIdAndIdIn(UserContext.getCurrentUser(), ids);
    }

    @PostMapping
    public ClassPlan createPlan(@RequestBody ClassPlan plan) {
        plan.setUserId(UserContext.getCurrentUser());
        return classPlanRepository.save(plan);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClassPlan> updatePlan(@PathVariable Long id, @RequestBody ClassPlan plan) {
        return classPlanRepository.findById(id)
            .filter(existing -> existing.getUserId().equals(UserContext.getCurrentUser()))
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
        classPlanRepository.deleteByIdAndUserId(id, UserContext.getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}