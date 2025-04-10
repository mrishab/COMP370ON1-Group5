package io.trishul.classplanner.ui.classplans;

import java.util.List;

import lombok.Data;

@Data
public class ClassPlanFilterRequest {
    private List<Long> gradPlanIds;
    private String programName;
    private String description;
    private int minCourses;
    private int maxCourses;
    private int minCredits;
    private int maxCredits;
    private List<String> terms;
    private int yearStart;
    private int yearEnd;
    private List<String> burdenCapacity;
    private List<String> classDistribution;
}
