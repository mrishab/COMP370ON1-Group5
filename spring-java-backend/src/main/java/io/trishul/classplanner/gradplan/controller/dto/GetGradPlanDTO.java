package io.trishul.classplanner.gradplan.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetGradPlanDTO {
    private Long id;
    private String fileName;
    private String programName;
    private String majorName;
    private Long creditsCompleted;
    private Long creditsRequired;
    private Double cgpa;
    private String programLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
