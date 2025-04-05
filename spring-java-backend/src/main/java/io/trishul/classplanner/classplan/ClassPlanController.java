package io.trishul.classplanner.classplan;

import io.trishul.classplanner.model.ClassPlan;
import io.trishul.classplanner.service.InMemoryStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classplan")
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
    public List<ClassPlan> getPlans(
        @RequestHeader("X-USER-ID") String userId,
        @RequestParam(required = false) List<Long> ids
    ) {
        return storage.getClassPlans(userId, ids);
    }

    @PostMapping("/create")
    public ClassPlan createPlan(
        @RequestHeader("X-USER-ID") String userId,
        @RequestBody ClassPlan plan
    ) {
        plan.setUserId(userId);
        return storage.saveClassPlan(plan);
    }

    @PutMapping("/{id}")
    public ClassPlan updatePlan(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id,
        @RequestBody ClassPlan plan
    ) {
        return storage.updateClassPlan(id, plan, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id
    ) {
        storage.deleteClassPlan(id, userId);
        return ResponseEntity.noContent().build();
    }
}