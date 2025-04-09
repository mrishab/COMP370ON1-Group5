package io.trishul.classplanner.classdetail.model;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class ClassDetail {
    @Id
    @SequenceGenerator(name = "class_detail_generator", sequenceName = "class_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_detail_generator")
    private Long id;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private String method;

    @ElementCollection
    @CollectionTable(name = "class_detail_schedule", 
        joinColumns = @JoinColumn(name = "class_detail_id"))
    @MapKeyColumn(name = "day")
    private Map<String, TimeSlot> schedule;
}