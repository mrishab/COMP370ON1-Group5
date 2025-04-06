package io.trishul.classplanner.availability.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class PutAvailabilityDTO {
    private List<PutAvailabilityDayDTO> days;

    @Data
    public static class PutAvailabilityDayDTO {
        private String day;
        private List<PutAvailabilityHourDTO> hours;
    }

    @Data
    public static class PutAvailabilityHourDTO {
        private int hourOfTheDay;
        private boolean isAvailable;
    }
}
