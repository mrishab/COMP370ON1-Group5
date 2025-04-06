package io.trishul.classplanner.courseclass.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ClassSchedule {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
