package io.trishul.classplanner.availability.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAvailabilityDTO {
    private List<GetAvailabilityDayDTO> days;

    @Data
    public static class GetAvailabilityDayDTO {
        private String day;
        private List<GetAvailabilityHourDTO> hours;
    }

    @Data
    public static class GetAvailabilityHourDTO {
        private int hourOfTheDay;
        private boolean isAvailable;
    }
}
