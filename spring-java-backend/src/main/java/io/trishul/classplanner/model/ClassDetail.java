package io.trishul.classplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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