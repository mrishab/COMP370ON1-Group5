package io.trishul.classplanner.classschedule.controller.dto;

import lombok.Data;

@Data
public class PutClassScheduleDTO {
    private String day;
    private String startTime;
    private String endTime;
}
