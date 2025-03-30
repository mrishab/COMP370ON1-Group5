package io.trishul.classplanner.api;

import android.os.Handler;
import android.os.Looper;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;

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
}