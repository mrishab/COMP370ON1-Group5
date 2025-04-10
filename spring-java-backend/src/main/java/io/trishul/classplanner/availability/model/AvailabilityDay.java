package io.trishul.classplanner.availability.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    @SequenceGenerator(name = "availability_day_generator", sequenceName = "availability_day_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_day_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    private String day;

    @OneToMany(mappedBy = "availabilityDay", cascade = CascadeType.ALL)
    private List<AvailabilityHour> hours;
}
