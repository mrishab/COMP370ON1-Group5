package io.trishul.classplanner.courseclass.controller.dto;

import java.util.List;

import io.trishul.classplanner.courseclass.model.ClassSchedule;
import lombok.Data;

@Data
public class PostCourseClassDTO {
    private String section;
    private String instructor;
    private String crn;
    private String room;
    private String method;
    private List<ClassSchedule> classSchedules;
    private Long courseId;
}
