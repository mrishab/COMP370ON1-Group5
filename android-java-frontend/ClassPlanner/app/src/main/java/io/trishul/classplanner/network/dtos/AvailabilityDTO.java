package io.trishul.classplanner.network.dtos;

import java.util.List;
import lombok.Data;

@Data
public class AvailabilityDTO {
    private List<AvailabilityDayDTO> days;

    @Data
    public static class AvailabilityDayDTO {
        private String day;
        private List<AvailabilityHourDTO> hours;
    }

    @Data
    public static class AvailabilityHourDTO {
        private Integer hourOfTheDay;
        private Boolean isAvailable;
    }
}
