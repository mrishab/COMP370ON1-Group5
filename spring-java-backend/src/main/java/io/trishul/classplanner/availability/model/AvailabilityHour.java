package io.trishul.classplanner.availability.model;

import jakarta.persistence.Column;
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
public class AvailabilityHour {
    @Id
    @SequenceGenerator(name = "availability_hour_generator", sequenceName = "availability_hour_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_hour_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "availability_day_id")
    private AvailabilityDay availabilityDay;

    @Column
    private Integer hourOfTheDay;

    @Column
    private boolean isAvailable;
}
