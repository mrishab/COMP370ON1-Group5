package io.trishul.classplanner.classdetail.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class TimeSlot {
    @Id
    @SequenceGenerator(name = "time_slot_generator", sequenceName = "time_slot_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_slot_generator")
    private Long id;

    @Column
    private String start;

    @Column(name = "_end")
    private String end;
}