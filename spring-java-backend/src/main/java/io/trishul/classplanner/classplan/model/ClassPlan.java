package io.trishul.classplanner.classplan.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.trishul.classplanner.availability.model.Availability;
import io.trishul.classplanner.classdetail.model.Course;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import io.trishul.classplanner.gradplan.model.GradPlan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
  @SequenceGenerator(name = "class_plan_generator", sequenceName = "class_plan_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_plan_generator")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "grad_plan_id")
  private GradPlan gradPlan;

  private String description;

  @OneToMany(mappedBy = "classPlan", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<Course> classes;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
  private Boolean archived;

  public void setClasses(List<Course> classes) {
    if (this.classes != null) {
      this.classes.forEach(course -> course.setClassPlan(null));
    } else {
      this.classes = new ArrayList<>();
    }

    this.classes.clear();
    if (classes != null) {
      classes.forEach(course -> course.setClassPlan(this));
      this.classes.addAll(classes);
    }
  }
}
