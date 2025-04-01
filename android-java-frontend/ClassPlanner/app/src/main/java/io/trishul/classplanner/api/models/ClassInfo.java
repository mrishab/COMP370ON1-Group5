package io.trishul.classplanner.api.models;

import java.util.Map;

public class ClassInfo {
    private String section;
    private String instructor;
    private String crn;
    private String room;
    private String method;
    private Map<String, TimeRange> schedule;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, TimeRange> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, TimeRange> schedule) {
        this.schedule = schedule;
    }
}