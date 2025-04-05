package io.trishul.classplanner.network;

import java.util.List;

public class ClassPlansResponse {
    private String planName;
    private List<String> courses;

    public String getPlanName() {
        return planName;
    }

    public List<String> getCourses() {
        return courses;
    }
}