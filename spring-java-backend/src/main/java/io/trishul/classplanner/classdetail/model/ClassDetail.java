package io.trishul.classplanner.classdetail.model;

import java.util.ArrayList;
import java.util.List;
import io.trishul.classplanner.classschedule.model.ClassSchedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class ClassDetail {
  @Id
  @SequenceGenerator(name = "class_detail_generator", sequenceName = "class_detail_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_detail_generator")
  private Long id;

  @Column
  private String section;

  @Column
  private String instructor;

  @Column
  private String room;

  @Column
  private String method;

  @Column
  private String crn;

  @Column
  private Integer credits;

  @OneToMany(mappedBy = "classDetail", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<ClassSchedule> schedule;

  public void setSchedule(List<ClassSchedule> schedule) {
    if (this.schedule != null) {
      this.schedule.forEach(s -> s.setClassDetail(null));
    } else {
      this.schedule = new ArrayList<>();
    }
    this.schedule.clear();
    if (schedule != null) {
      schedule.forEach(s -> s.setClassDetail(this));
      this.schedule.addAll(schedule);
    }
  }
}
