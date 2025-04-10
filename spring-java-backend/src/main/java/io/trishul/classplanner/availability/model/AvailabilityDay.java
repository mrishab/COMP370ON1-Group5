package io.trishul.classplanner.availability.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class AvailabilityDay {
  @Id
  @SequenceGenerator(name = "availability_day_generator",
      sequenceName = "availability_day_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_day_generator")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "availability_id", nullable = false)
  private Availability availability;

  @Column
  private String day;

  @OneToMany(mappedBy = "availabilityDay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AvailabilityHour> hours;

  public void setHours(List<AvailabilityHour> hours) {
    if (this.hours != null) {
      this.hours.forEach(hour -> hour.setAvailabilityDay(null));
    } else {
      this.hours = new ArrayList<>();
    }

    this.hours.clear();
    if (hours != null) {
      hours.forEach(hour -> hour.setAvailabilityDay(this));
      this.hours.addAll(hours);
    }
  }
}
