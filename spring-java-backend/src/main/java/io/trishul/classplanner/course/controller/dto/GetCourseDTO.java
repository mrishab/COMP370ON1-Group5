package io.trishul.classplanner.course.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetCourseDTO {
    private Long id;
    private String title;
    private String description;
    private String courseCode;
    private String credits;
    private String courseNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
