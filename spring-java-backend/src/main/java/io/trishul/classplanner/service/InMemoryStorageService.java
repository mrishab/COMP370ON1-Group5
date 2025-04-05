package io.trishul.classplanner.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import io.trishul.classplanner.model.ClassPlan;
import io.trishul.classplanner.model.GradPlan;
import io.trishul.classplanner.user.User;

@Service
public class InMemoryStorageService {
    private final Map<Long, GradPlan> gradPlans = new ConcurrentHashMap<>();
    private final Map<Long, ClassPlan> classPlans = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();

    private final AtomicLong gradPlanId = new AtomicLong(1);
    private final AtomicLong classPlanId = new AtomicLong(1);
    private final AtomicLong userIdGenerator = new AtomicLong(1);

    public List<GradPlan> getGradPlansByUser(String userId) {
        return gradPlans.values().stream().filter(p -> p.getUserId().equals(userId)).toList();
    }

    public GradPlan saveGradPlan(String userId, String fileName) {
        Long id = gradPlanId.getAndIncrement();
        GradPlan plan = new GradPlan(id, userId, fileName, LocalDateTime.now(), LocalDateTime.now());
        gradPlans.put(id, plan);
        return plan;
    }

    public void deleteGradPlan(Long id, String userId) {
        GradPlan plan = gradPlans.get(id);
        if (plan != null && plan.getUserId().equals(userId)) {
            gradPlans.remove(id);
        }
    }

    public List<ClassPlan> getClassPlans(String userId, List<Long> ids) {
        return classPlans.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .filter(p -> ids == null || ids.contains(p.getId()))
                .toList();
    }

    public ClassPlan saveClassPlan(ClassPlan plan) {
        plan.setId(classPlanId.getAndIncrement());
        classPlans.put(plan.getId(), plan);
        return plan;
    }

    public ClassPlan updateClassPlan(Long id, ClassPlan updated, String userId) {
        if (classPlans.containsKey(id) && classPlans.get(id).getUserId().equals(userId)) {
            updated.setId(id);
            updated.setUserId(userId);
            classPlans.put(id, updated);
            return updated;
        }
        return null;
    }

    public void deleteClassPlan(Long id, String userId) {
        ClassPlan plan = classPlans.get(id);
        if (plan != null && plan.getUserId().equals(userId)) {
            classPlans.remove(id); 
        }
    }    

    public User getUser(String userId) {
        return users.get(userId);
    }

    public User updateUser(String userId, User updated) {
        updated.setId(Long.parseLong(userId)); 
        users.put(userId, updated);
        return updated;
    }       
    
    public User createUser(User user) {
        Long generatedId = userIdGenerator.getAndIncrement();
        user.setId(generatedId);
        users.put(String.valueOf(generatedId), user);
        return user;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return users.values().stream()
            .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
            .findFirst()
            .orElse(null);
    }
}