package io.trishul.classplanner.classplan.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classplan.model.BurdenCapacity;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface BurdenCapacityRepository extends BaseRepository<BurdenCapacity, Long> {
}
