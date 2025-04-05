package io.trishul.classplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPlan {
    private Long id;
    private String userId;
    private Long gradPlanId;
    private String workload;
    private String classDistribution;
    private List<ClassEntry> classes;
}