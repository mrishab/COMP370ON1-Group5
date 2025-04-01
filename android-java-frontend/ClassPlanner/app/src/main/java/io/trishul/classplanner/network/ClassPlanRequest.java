package io.trishul.classplanner.network;

public class ClassPlanRequest {
    private String major;
    private int year;
    private int numberOfTerms;

    public ClassPlanRequest(String major, int year, int numberOfTerms) {
        this.major = major;
        this.year = year;
        this.numberOfTerms = numberOfTerms;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfTerms() {
        return numberOfTerms;
    }
}