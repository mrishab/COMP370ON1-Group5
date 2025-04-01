package io.trishul.classplanner.api.models;

import com.google.gson.annotations.SerializedName;

public class GradPlan {
    @SerializedName("gradPlanId")
    private Long gradPlanId;
    
    @SerializedName("fileName")
    private String fileName;
    
    @SerializedName("programName")
    private String programName;
    
    @SerializedName("creditsCompleted")
    private int creditsCompleted;
    
    @SerializedName("creditsRequired")
    private int creditsRequired;
    
    @SerializedName("currentGpa")
    private double currentGpa;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    // Getters and setters
    public Long getGradPlanId() {
        return gradPlanId;
    }
    
    public void setGradPlanId(Long gradPlanId) {
        this.gradPlanId = gradPlanId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getProgramName() {
        return programName;
    }
    
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    public int getCreditsCompleted() {
        return creditsCompleted;
    }
    
    public void setCreditsCompleted(int creditsCompleted) {
        this.creditsCompleted = creditsCompleted;
    }
    
    public int getCreditsRequired() {
        return creditsRequired;
    }
    
    public void setCreditsRequired(int creditsRequired) {
        this.creditsRequired = creditsRequired;
    }
    
    public double getCurrentGpa() {
        return currentGpa;
    }
    
    public void setCurrentGpa(double currentGpa) {
        this.currentGpa = currentGpa;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
