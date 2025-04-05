package io.trishul.classplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntry {
    private String course;
    private String courseNumber;
    private String description;
    private ClassDetail classDetail;
}