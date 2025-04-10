package io.trishul.classplanner.availability.controller.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer hourOfTheDay;

    @JsonProperty("isAvailable")
    private Boolean isAvailable;
  }
}
