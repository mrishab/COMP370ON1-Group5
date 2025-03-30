package io.trishul.classplanner.api.models;

import java.util.List;
import java.util.Map;

public class PlanCreationRequest {
    private int gradPlanId;
    private Map<String, List<Boolean>> availability;
    private String burdenCapacity;
    private String classDistribution;

    public int getGradPlanId() {
        return gradPlanId;
    }

    public void setGradPlanId(int gradPlanId) {
        this.gradPlanId = gradPlanId;
    }

    public Map<String, List<Boolean>> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, List<Boolean>> availability) {
        this.availability = availability;
    }

    public String getBurdenCapacity() {
        return burdenCapacity;
    }

    public void setBurdenCapacity(String burdenCapacity) {
        this.burdenCapacity = burdenCapacity;
    }

    public String getClassDistribution() {
        return classDistribution;
    }

    public void setClassDistribution(String classDistribution) {
        this.classDistribution = classDistribution;
    }
}
