package io.trishul.classplanner.api.models;

import com.google.gson.annotations.SerializedName;

public class ClassPlan {
    @SerializedName("classPlanId")
    private Long classPlanId;
    
    @SerializedName("gradPlanId")
    private Long gradPlanId;
    
    @SerializedName("programName")
    private String programName;
    
    @SerializedName("totalCourses")
    private int totalCourses;
    
    @SerializedName("totalCredits")
    private int totalCredits;
    
    @SerializedName("term")
    private String term;
    
    @SerializedName("year")
    private int year;
    
    @SerializedName("burdenCapacity")
    private String burdenCapacity;
    
    @SerializedName("classDistribution")
    private String classDistribution;

    // Getters and setters
    public Long getClassPlanId() { return classPlanId; }
    public void setClassPlanId(Long value) { this.classPlanId = value; }
    public Long getGradPlanId() { return gradPlanId; }
    public void setGradPlanId(Long value) { this.gradPlanId = value; }
    public String getProgramName() { return programName; }
    public void setProgramName(String value) { this.programName = value; }
    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int value) { this.totalCourses = value; }
    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int value) { this.totalCredits = value; }
    public String getTerm() { return term; }
    public void setTerm(String value) { this.term = value; }
    public int getYear() { return year; }
    public void setYear(int value) { this.year = value; }
    public String getBurdenCapacity() { return burdenCapacity; }
    public void setBurdenCapacity(String value) { this.burdenCapacity = value; }
    public String getClassDistribution() { return classDistribution; }
    public void setClassDistribution(String value) { this.classDistribution = value; }
}
