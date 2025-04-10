package io.trishul.classplanner.courseclass.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.trishul.classplanner.classschedule.model.ClassSchedule;
import io.trishul.classplanner.course.model.Course;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class CourseClass {
    @Id
    @SequenceGenerator(name = "course_class_generator", sequenceName = "course_class_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_class_generator")
    private Long id;

    @Column
    private String section;

    @Column
    private String instructor;

    @Column
    private String crn;

    @Column
    private String room;

    @Column
    private String method;

    @ElementCollection
    @CollectionTable(name = "course_class_schedules", joinColumns = @JoinColumn(name = "course_class_id"))
    private List<ClassSchedule> classSchedules;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
