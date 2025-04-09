package io.trishul.classplanner.gradplan.controller.dto;

import lombok.Data;

@Data
public class PostGradPlanDTO {
    private String fileName;
    private String mimeType;
    private String programName;
    private String majorName;
    private long creditsCompleted;
    private long creditsRequired;
    private double cgpa;
    private String calendarTermSemester;
    private int calendarTermYear;
    private String programLevel;
}
