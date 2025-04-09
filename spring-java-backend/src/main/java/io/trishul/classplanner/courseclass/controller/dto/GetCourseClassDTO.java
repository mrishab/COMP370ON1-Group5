package io.trishul.classplanner.courseclass.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import io.trishul.classplanner.course.controller.dto.GetCourseDTO;
import lombok.Data;

@Data
public class GetCourseClassDTO {
    private Long id;
    private String section;
    private String instructor;
    private String crn;
    private String room;
    private String method;
    private List<GetClassScheduleDTO> classSchedules;
    private GetCourseDTO course;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
