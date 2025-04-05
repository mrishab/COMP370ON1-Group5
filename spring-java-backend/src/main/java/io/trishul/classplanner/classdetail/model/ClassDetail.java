package io.trishul.classplanner.classdetail.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetail {
    private String section;
    private String instructor;
    private String room;
    private String method;
    private Map<String, TimeSlot> schedule;
}