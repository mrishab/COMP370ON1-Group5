package io.trishul.classplanner.gradplan.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.classplanner.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class GradPlan {
    @Id
    @SequenceGenerator(name = "grad_plan_generator", sequenceName = "grad_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grad_plan_generator")
    private Long id;

    @Column
    private String fileName;

    @Column
    private String programName;

    @Column
    private String majorName;

    @Column
    private Long creditsCompleted;

    @Column
    private Long creditsRequired;

    @Column
    private Double cgpa;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column
    private String programLevel;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Boolean archived;

    @Lob
    @Column
    private String pdfContentBase64;

    @Column
    private String details;
}