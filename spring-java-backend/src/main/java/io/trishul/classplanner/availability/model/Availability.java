package io.trishul.classplanner.availability.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Embeddable
@Data
public class Availability {
    @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL)
    private List<AvailabilityDay> days;
}
