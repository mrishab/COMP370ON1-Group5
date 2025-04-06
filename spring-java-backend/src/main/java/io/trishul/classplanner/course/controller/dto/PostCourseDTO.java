package io.trishul.classplanner.course.controller.dto;

import lombok.Data;

@Data
public class PostCourseDTO {
    private String title;
    private String description;
    private String courseCode;
    private String credits;
    private String courseNumber;
}
