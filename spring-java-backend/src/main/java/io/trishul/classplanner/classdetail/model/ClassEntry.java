package io.trishul.classplanner.classdetail.model;

import io.trishul.classplanner.classplan.model.ClassPlan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class ClassEntry {
    @Id
    @SequenceGenerator(name = "class_entry_generator", sequenceName = "class_entry_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_entry_generator")
    private Long id;

    @Column
    private String course;

    @Column
    private String courseNumber;

    private String description;

    @ManyToOne
    @JoinColumn(name = "class_detail_id")
    private ClassDetail classDetail;

    @ManyToOne
    @JoinColumn(name = "class_plan_id")
    private ClassPlan classPlan;
}