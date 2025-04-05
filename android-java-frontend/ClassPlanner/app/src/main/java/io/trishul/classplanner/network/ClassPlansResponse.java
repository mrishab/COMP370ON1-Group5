package io.trishul.classplanner.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import io.trishul.classplanner.api.models.ClassPlan;

public class ClassPlansResponse {
    @SerializedName("classPlans")
    private List<ClassPlan> classPlans;

    public List<ClassPlan> getClassPlans() {
        return classPlans;
    }

    public void setClassPlans(List<ClassPlan> classPlans) {
        this.classPlans = classPlans;
    }
}