package io.trishul.classplanner.api.models;

import java.util.List;

public class ClassPlanFilterRequest {
    private List<Integer> gradPlanIds;
    private String programName;
    private String description;
    private int minCourses;
    private int maxCourses;
    private int minCredits;
    private int maxCredits;
    private List<String> terms;
    private int yearStart;
    private int yearEnd;
    private List<String> burdenCapacity;
    private List<String> classDistribution;

    // Getters and setters
    public List<Integer> getGradPlanIds() { return gradPlanIds; }
    public void setGradPlanIds(List<Integer> value) { this.gradPlanIds = value; }
    public String getProgramName() { return programName; }
    public void setProgramName(String value) { this.programName = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public int getMinCourses() { return minCourses; }
    public void setMinCourses(int value) { this.minCourses = value; }
    public int getMaxCourses() { return maxCourses; }
    public void setMaxCourses(int value) { this.maxCourses = value; }
    public int getMinCredits() { return minCredits; }
    public void setMinCredits(int value) { this.minCredits = value; }
    public int getMaxCredits() { return maxCredits; }
    public void setMaxCredits(int value) { this.maxCredits = value; }
    public List<String> getTerms() { return terms; }
    public void setTerms(List<String> value) { this.terms = value; }
    public int getYearStart() { return yearStart; }
    public void setYearStart(int value) { this.yearStart = value; }
    public int getYearEnd() { return yearEnd; }
    public void setYearEnd(int value) { this.yearEnd = value; }
    public List<String> getBurdenCapacity() { return burdenCapacity; }
    public void setBurdenCapacity(List<String> value) { this.burdenCapacity = value; }
    public List<String> getClassDistribution() { return classDistribution; }
    public void setClassDistribution(List<String> value) { this.classDistribution = value; }
}
