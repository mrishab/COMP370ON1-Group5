package io.trishul.classplanner.classplan.controller.dto;

import java.util.List;

import io.trishul.classplanner.classdetail.model.ClassEntry;
import io.trishul.classplanner.classplan.model.Availability;
import io.trishul.classplanner.classplan.model.BurdenCapacity;
import io.trishul.classplanner.classplan.model.ClassDistribution;
import lombok.Data;

@Data
public class PostClassPlanDTO {
    private Long gradPlanId;
    private String description;
    private List<ClassEntry> classes;
    private Availability availability;
    private ClassDistribution classDistribution;
    private BurdenCapacity burdenCapacity;
}
