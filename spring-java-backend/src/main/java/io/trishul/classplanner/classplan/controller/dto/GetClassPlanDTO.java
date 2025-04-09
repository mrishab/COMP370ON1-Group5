package io.trishul.classplanner.classplan.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO;
import io.trishul.classplanner.classdetail.dto.classentry.GetClassEntryDTO;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import io.trishul.classplanner.gradplan.controller.dto.GetGradPlanDTO;
import lombok.Data;

@Data
public class GetClassPlanDTO {
    private Long id;
    private GetGradPlanDTO gradPlan;
    private String description;
    private List<GetClassEntryDTO> classes;
    private GetAvailabilityDTO availability;
    private ClassDistribution classDistribution;
    private BurdenCapacity burdenCapacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
