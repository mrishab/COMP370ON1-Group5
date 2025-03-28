package io.trishul.classplanner.classplan;

import lombok.Data;

@Data
public class ClassPlanRequest {
    private String major;
    private int year;
    private int numberOfTerms;
}