package io.trishul.classplanner.gradplan.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.classplanner.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradPlan {
    @Id
    @SequenceGenerator(name = "grad_plan_generator", sequenceName = "grad_plan_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grad_plan_generator")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private String programName;

    @Column(nullable = false)
    private String majorName;

    @Column(nullable = false)
    private long creditsCompleted;

    @Column(nullable = false)
    private long creditsRequired;

    private double cgpa;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime auditedAt;

    @Column(nullable = false)
    private String calendarTermSemester;

    @Column(nullable = false)
    private int calendarTermYear;

    @Column(nullable = false)
    private String programLevel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true)
    private boolean archived;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String pdfContentBase64;
}