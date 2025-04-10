package io.trishul.classplanner.availability.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Availability {
  @Id
  @SequenceGenerator(name = "availability_generator", sequenceName = "availability_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_generator")
  private Long id;

  @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AvailabilityDay> days;

  public void setDays(List<AvailabilityDay> days) {
    if (this.days != null) {
      this.days.forEach(day -> day.setAvailability(null));
    } else {
      this.days = new ArrayList<>();
    }
    this.days.clear();
    if (days != null) {
      days.forEach(day -> day.setAvailability(this));
      this.days.addAll(days);
    }
  }
}
