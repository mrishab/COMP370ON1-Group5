package io.trishul.classplanner.classdetail.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.TimeSlot;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface TimeSlotRepository extends BaseRepository<TimeSlot, Long> {
}
