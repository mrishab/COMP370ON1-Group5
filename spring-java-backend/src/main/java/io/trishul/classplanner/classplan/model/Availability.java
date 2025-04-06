package io.trishul.classplanner.classplan.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Availability {
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private boolean morning;
    private boolean afternoon;
    private boolean evening;
}
