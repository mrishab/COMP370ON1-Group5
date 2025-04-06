package io.trishul.classplanner.classplan.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ClassDistribution {
    private int coreCourses;
    private int electiveCourses;
    private int generalEducationCourses;
}
