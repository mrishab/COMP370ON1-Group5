package io.trishul.classplanner.api;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;
import io.trishul.classplanner.api.models.GradPlansRequest;
import io.trishul.classplanner.api.models.GradPlansResponse;
import io.trishul.classplanner.api.models.GradPlan;

public class BackendClient {
    private static BackendClient instance;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();

    private BackendClient() {
        // Private constructor to enforce singleton pattern
    }
    
    public static BackendClient getInstance() {
        if (instance == null) {
            instance = new BackendClient();
        }
        return instance;
    }

    public CompletableFuture<PlanCreationResponse> createClassPlan(PlanCreationRequest request) {
        CompletableFuture<PlanCreationResponse> future = new CompletableFuture<>();
        
        executor.execute(() -> {
            try {
                // Simulate network delay
                Thread.sleep(4000);
                
                // Simulate random success/failure
                boolean isSuccess = random.nextInt(10) < 8; // 80% success rate
                
                PlanCreationResponse response = new PlanCreationResponse();
                if (isSuccess) {
                    response.setSuccess(true);
                    response.setMessage("Class plan created successfully");
                    response.setPlanId("plan_" + System.currentTimeMillis());
                } else {
                    response.setSuccess(false);
                    response.setMessage("Failed to create class plan. Server error.");
                }
                
                mainHandler.post(() -> future.complete(response));
            } catch (Exception e) {
                mainHandler.post(() -> future.completeExceptionally(e));
            }
        });
        
        return future;
    }

    public CompletableFuture<GradPlansResponse> getGradPlans(GradPlansRequest request) {
        CompletableFuture<GradPlansResponse> future = new CompletableFuture<>();
        
        executor.execute(() -> {
            try {
                // Simulate network delay
                Thread.sleep(2000);
                
                // Simulate API response
                GradPlansResponse response = new GradPlansResponse();
                List<GradPlan> mockGradPlans = new ArrayList<>();
                
                // Add mock data
                GradPlan plan1 = new GradPlan();
                plan1.setGradPlanId(1L);
                plan1.setProgramName("Computer Science");
                plan1.setCreditsCompleted(90);
                plan1.setCreditsRequired(120);
                plan1.setCurrentGpa(3.8);
                plan1.setCreatedAt("2023-01-15T10:00:00Z");
                
                GradPlan plan2 = new GradPlan();
                plan2.setGradPlanId(2L);
                plan2.setProgramName("Mechanical Engineering");
                plan2.setCreditsCompleted(100);
                plan2.setCreditsRequired(130);
                plan2.setCurrentGpa(3.5);
                plan2.setCreatedAt("2023-02-20T14:30:00Z");
                
                mockGradPlans.add(plan1);
                mockGradPlans.add(plan2);
                
                response.setGradPlans(mockGradPlans);
                
                mainHandler.post(() -> future.complete(response));
            } catch (Exception e) {
                mainHandler.post(() -> future.completeExceptionally(e));
            }
        });
        
        return future;
    }
}