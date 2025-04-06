package io.trishul.classplanner.availability.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class GetAvailabilityDTO {
    private Long id;
    private List<GetAvailabilityDayDTO> days;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class GetAvailabilityDayDTO {
        private Long id;
        private String day;
        private List<GetAvailabilityHourDTO> hours;
    }

    @Data
    public static class GetAvailabilityHourDTO {
        private Long id;
        private int hourOfTheDay;
        private boolean isAvailable;
    }
}
