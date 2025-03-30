package io.trishul.classplanner.api.models;

import java.util.List;

public class PlanCreationResponse {
    private int id;
    private List<ClassDetail> classes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ClassDetail> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassDetail> classes) {
        this.classes = classes;
    }
}
