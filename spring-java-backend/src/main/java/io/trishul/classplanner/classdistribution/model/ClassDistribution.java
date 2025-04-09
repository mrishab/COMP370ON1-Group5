package io.trishul.classplanner.classdistribution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "class_distribution")
@Getter
@Setter
public class ClassDistribution {

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String input;

    @Column(nullable = true)
    private boolean archived;
}
