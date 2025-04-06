package io.trishul.classplanner.classplan.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BurdenCapacity {
    private int maxCreditsPerSemester;
    private int maxCoursesPerSemester;
    private int maxCoursesPerDay;
}
