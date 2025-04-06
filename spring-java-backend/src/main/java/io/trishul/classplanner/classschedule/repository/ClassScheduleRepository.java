package io.trishul.classplanner.classschedule.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classschedule.model.ClassSchedule;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface ClassScheduleRepository extends BaseRepository<ClassSchedule, Long> {
}
