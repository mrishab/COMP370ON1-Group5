package io.trishul.classplanner.network.dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ClassPlanDTO {
    @Data
    public static class Get {
        private Long id;
        private GradPlanDTO.Get gradPlan;
        private String description;
        private List<CourseDTO> classes;
        private AvailabilityDTO availability;
        private ClassDistribution classDistribution;
        private BurdenCapacity burdenCapacity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    public static class Post {
        private Long gradPlanId;
        private String description;
        private AvailabilityDTO availability;
        private ClassDistribution classDistribution;
        private BurdenCapacity burdenCapacity;
    }
}
