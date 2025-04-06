package io.trishul.classplanner.classplan.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.classplanner.classdetail.model.ClassEntry;
import io.trishul.classplanner.gradplan.model.GradPlan;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class ClassPlan {
    @Id
    @SequenceGenerator(name = "class_plan_generator", sequenceName = "class_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_plan_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grad_plan_id", nullable = false)
    private GradPlan gradPlan;

    private String description;

    @ElementCollection
    private List<ClassEntry> classes;

    @Embedded
    private Availability availability;

    @Embedded
    private ClassDistribution classDistribution;

    @Embedded
    private BurdenCapacity burdenCapacity;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private boolean archived;
}