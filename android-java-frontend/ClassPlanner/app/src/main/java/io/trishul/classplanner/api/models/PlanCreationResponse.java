package io.trishul.classplanner.api.models;

import java.io.Serializable;
import java.util.List;

public class PlanCreationResponse implements Serializable {
    private boolean success;
    private String message;
    private String planId;
    private int id;
    private List<ClassDetail> classes;

    public PlanCreationResponse() {
        this.success = false;
        this.message = "";
    }

    public PlanCreationResponse(boolean success, String message, String planId) {
        this.success = success;
        this.message = message;
        this.planId = planId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

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
