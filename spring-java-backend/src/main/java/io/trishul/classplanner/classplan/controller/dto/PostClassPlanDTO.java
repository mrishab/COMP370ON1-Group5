package io.trishul.classplanner.classplan.controller.dto;

import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import lombok.Data;

@Data
public class PostClassPlanDTO {
  private Long gradPlanId;
  private String description;
  private PostAvailabilityDTO availability;
  private ClassDistribution classDistribution;
  private BurdenCapacity burdenCapacity;
  private Integer desiredNumOfCourses;
}
