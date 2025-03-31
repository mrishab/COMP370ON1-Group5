package io.trishul.classplanner.classplan;

import lombok.Data;
import java.util.List;

@Data
public class ClassPlanResponse {
    private String planName;
    private List<String> courses;
}