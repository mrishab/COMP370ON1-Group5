package io.trishul.classplanner.classplan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.config.UserContext;
import io.trishul.classplanner.model.ClassPlan;
import io.trishul.classplanner.service.InMemoryStorageService;

@RestController
@RequestMapping("/api/v1/classplans")
public class ClassPlanController {

    @Autowired
    private InMemoryStorageService storage;

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

    @GetMapping("/all")
    public List<ClassPlan> getPlans(@RequestParam(required = false) List<Long> ids) {
        return storage.getClassPlans(UserContext.getCurrentUser(), ids);
    }

    @PostMapping("/create")
    public ClassPlan createPlan(@RequestBody ClassPlan plan) {
        plan.setUserId(UserContext.getCurrentUser());
        return storage.saveClassPlan(plan);
    }

    @PutMapping("/{id}")
    public ClassPlan updatePlan(@PathVariable Long id, @RequestBody ClassPlan plan) {
        return storage.updateClassPlan(id, plan, UserContext.getCurrentUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        storage.deleteClassPlan(id, UserContext.getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}