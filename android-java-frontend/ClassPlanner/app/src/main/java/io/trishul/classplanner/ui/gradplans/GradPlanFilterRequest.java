package io.trishul.classplanner.ui.gradplans;

import java.util.List;

public class GradPlanFilterRequest {
    private int minCreditsRequired;
    private int maxCreditsRequired;
    private int minCreditsCompleted;
    private int maxCreditsCompleted;
    private double minCGPA;
    private double maxCGPA;
    private List<String> levels;
    private String degree;
    private String major;
    private List<String> terms;
    private int yearStart;
    private int yearEnd;

    public GradPlanFilterRequest() {
        // Default constructor
    }

    // Getters and setters
    public int getMinCreditsRequired() { return minCreditsRequired; }
    public void setMinCreditsRequired(int value) { this.minCreditsRequired = value; }
    public int getMaxCreditsRequired() { return maxCreditsRequired; }
    public void setMaxCreditsRequired(int value) { this.maxCreditsRequired = value; }
    public int getMinCreditsCompleted() { return minCreditsCompleted; }
    public void setMinCreditsCompleted(int value) { this.minCreditsCompleted = value; }
    public int getMaxCreditsCompleted() { return maxCreditsCompleted; }
    public void setMaxCreditsCompleted(int value) { this.maxCreditsCompleted = value; }
    public double getMinCGPA() { return minCGPA; }
    public void setMinCGPA(double value) { this.minCGPA = value; }
    public double getMaxCGPA() { return maxCGPA; }
    public void setMaxCGPA(double value) { this.maxCGPA = value; }
    public List<String> getLevels() { return levels; }
    public void setLevels(List<String> value) { this.levels = value; }
    public String getDegree() { return degree; }
    public void setDegree(String value) { this.degree = value; }
    public String getMajor() { return major; }
    public void setMajor(String value) { this.major = value; }
    public List<String> getTerms() { return terms; }
    public void setTerms(List<String> value) { this.terms = value; }
    public int getYearStart() { return yearStart; }
    public void setYearStart(int value) { this.yearStart = value; }
    public int getYearEnd() { return yearEnd; }
    public void setYearEnd(int value) { this.yearEnd = value; }
}
