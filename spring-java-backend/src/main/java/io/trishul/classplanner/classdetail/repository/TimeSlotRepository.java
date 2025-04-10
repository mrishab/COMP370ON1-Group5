package io.trishul.classplanner.classdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
