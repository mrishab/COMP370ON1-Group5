package io.trishul.classplanner.network.dtos;

import java.util.List;
import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String subject;
    private String number;
    private String title;
    private ClassDetailDTO classDetail;

    @Data
    public static class ClassDetailDTO {
        private String term;
        private String crn;
        private String instructor;
        private Integer credits;
        private List<ClassScheduleDTO> schedule;
        private String room;
        private String method;
        private String section;
    }

    @Data
    public static class ClassScheduleDTO {
        private Long id;
        private String day;
        private String startTime;
        private String endTime;
    }
}
