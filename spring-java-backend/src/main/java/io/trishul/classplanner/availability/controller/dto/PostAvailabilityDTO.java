package io.trishul.classplanner.availability.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostAvailabilityDTO {
    private List<PostAvailabilityDayDTO> days;

    @Data
    public static class PostAvailabilityDayDTO {
        private String day;
        private List<PostAvailabilityHourDTO> hours;
    }

    @Data
    public static class PostAvailabilityHourDTO {
        private Integer hourOfTheDay;
        private boolean isAvailable;
    }
}
