package io.trishul.classplanner.gradplan.model;

import java.time.LocalDateTime;

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
public class GradPlan {
    @Id
    @SequenceGenerator(name = "grad_plan_generator", sequenceName = "grad_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grad_plan_generator")
    private Long id;
    private String userId;
    private String fileName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}