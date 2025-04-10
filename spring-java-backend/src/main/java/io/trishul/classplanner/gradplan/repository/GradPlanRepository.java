package io.trishul.classplanner.gradplan.repository;

import org.springframework.stereotype.Repository;
import io.trishul.classplanner.common.repository.BaseRepository;
import io.trishul.classplanner.gradplan.model.GradPlan;

@Repository
public interface GradPlanRepository extends BaseRepository<GradPlan, Long> {
}
