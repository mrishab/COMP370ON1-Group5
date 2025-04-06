package io.trishul.classplanner.classschedule.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetClassScheduleDTO {
    private Long id;
    private String day;
    private String startTime;
    private String endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
