package io.trishul.classplanner.classplan.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClassPlanResponse {
    private String planName;
    private List<String> courses;
}