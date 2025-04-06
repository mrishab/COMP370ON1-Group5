package io.trishul.classplanner.course.controller.dto;

import lombok.Data;

@Data
public class PutCourseDTO {
    private String title;
    private String description;
    private String courseCode;
    private String credits;
    private String courseNumber;
}
