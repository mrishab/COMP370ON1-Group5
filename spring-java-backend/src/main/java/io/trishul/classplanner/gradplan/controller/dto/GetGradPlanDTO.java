package io.trishul.classplanner.gradplan.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetGradPlanDTO {
    private Long id;
    private String fileName;
    private String programName;
    private String majorName;
    private long creditsCompleted;
    private long creditsRequired;
    private double cgpa;
    private LocalDateTime auditedAt;
    private String calendarTermSemester;
    private int calendarTermYear;
    private String programLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
