package io.trishul.classplanner.api;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;

public class BackendClient {
    private static BackendClient instance;

    private BackendClient() {
        // Private constructor to enforce singleton pattern
    }
    
    public static BackendClient getInstance() {
        if (instance == null) {
            instance = new BackendClient();
        }
        return instance;
    }

    public PlanCreationResponse createClassPlan(PlanCreationRequest request) {

        return new PlanCreationResponse(); // Placeholder
    }
}