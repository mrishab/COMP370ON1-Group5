package io.trishul.classplanner.network.dtos;

import java.time.LocalDateTime;

import lombok.Data;

public class GradPlanDTO {
    @Data
    public static class Get {
        private Long id;
        private String fileName;
        private String programName;
        private String majorName;
        private Long creditsCompleted;
        private Long creditsRequired;
        private Double cgpa;
        private String programLevel;
        private String createdAt;
        private String updatedAt;
    }
}
