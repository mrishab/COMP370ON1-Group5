package io.trishul.classplanner.classschedule.model;

import io.trishul.classplanner.classdetail.model.ClassDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class ClassSchedule {
  @Id
  @SequenceGenerator(name = "class_schedule_generator", sequenceName = "class_schedule_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_schedule_generator")
  private Long id;

  @Column
  private String day;

  @Column
  private String startTime;

  @Column
  private String endTime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "class_detail_id")
  private ClassDetail classDetail;
}
