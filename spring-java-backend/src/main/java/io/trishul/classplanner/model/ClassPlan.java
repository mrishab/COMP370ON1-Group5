package io.trishul.classplanner.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPlan {
    @Id
    @SequenceGenerator(name = "class_plan_generator", sequenceName = "class_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_plan_generator")
    private Long id;
    private String userId;
    private Long gradPlanId;
    private String workload;
    private String classDistribution;
    
    @ElementCollection
    @Column(columnDefinition = "json")
    private List<ClassEntry> classes;
}