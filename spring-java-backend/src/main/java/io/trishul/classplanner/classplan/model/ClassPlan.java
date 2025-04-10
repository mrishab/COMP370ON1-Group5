package io.trishul.classplanner.classplan.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.classplanner.availability.model.Availability;
import io.trishul.classplanner.classdetail.model.ClassEntry;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.gradplan.model.GradPlan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @JoinColumn(name = "grad_plan_id")
    private GradPlan gradPlan;

    private String description;

    @OneToMany(mappedBy = "classPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassEntry> classes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_distribution")
    private ClassDistribution classDistribution;

    @Enumerated(EnumType.STRING)
    @Column(name = "burden_capacity")
    private BurdenCapacity burdenCapacity;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column
    private boolean archived;
}