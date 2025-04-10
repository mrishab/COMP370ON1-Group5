package io.trishul.classplanner.network.dtos;

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
        private String schedule;
        private String location;
    }
}
