package io.trishul.classplanner.classdetail.model;

import io.trishul.classplanner.classplan.model.ClassPlan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Course {
  @Id
  @SequenceGenerator(name = "course_generator", sequenceName = "course_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
  private Long id;

  @Column(name = "_subject")
  private String subject;

  @Column
  private Integer number;

  @Column
  private String title;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "class_plan_id")
  private ClassPlan classPlan;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "class_detail_id")
  private ClassDetail classDetail;
}
