package io.trishul.classplanner.classschedule.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class ClassSchedule {
    @Id
    @SequenceGenerator(name = "class_schedule_generator", sequenceName = "class_schedule_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_schedule_generator")
    private Long id;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private Boolean archived;
}
