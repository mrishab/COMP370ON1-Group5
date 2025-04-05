package io.trishul.classplanner.classplan.dto;

import lombok.Data;

@Data
public class ClassPlanRequest {
    private String major;
    private int year;
    private int numberOfTerms;
}