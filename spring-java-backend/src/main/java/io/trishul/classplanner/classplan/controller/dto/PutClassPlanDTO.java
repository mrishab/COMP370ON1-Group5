package io.trishul.classplanner.classplan.controller.dto;

import io.trishul.classplanner.availability.controller.dto.PutAvailabilityDTO;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import lombok.Data;

@Data
public class PutClassPlanDTO {
    private String description;
    private PutAvailabilityDTO availability;
    private ClassDistribution classDistribution;
    private BurdenCapacity burdenCapacity;
}
