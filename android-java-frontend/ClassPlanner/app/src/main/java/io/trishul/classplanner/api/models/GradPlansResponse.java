package io.trishul.classplanner.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GradPlansResponse {
    @SerializedName("gradPlans")
    private List<GradPlan> gradPlans;

    public List<GradPlan> getGradPlans() {
        return gradPlans;
    }

    public void setGradPlans(List<GradPlan> gradPlans) {
        this.gradPlans = gradPlans;
    }
}
