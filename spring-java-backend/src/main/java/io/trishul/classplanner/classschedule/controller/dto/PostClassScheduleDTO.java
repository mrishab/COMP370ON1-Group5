package io.trishul.classplanner.classschedule.controller.dto;

import lombok.Data;

@Data
public class PostClassScheduleDTO {
    private String day;
    private String startTime;
    private String endTime;
}
