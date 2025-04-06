package io.trishul.classplanner.classplan.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface ClassPlanRepository extends BaseRepository<ClassPlan, Long> {
}
