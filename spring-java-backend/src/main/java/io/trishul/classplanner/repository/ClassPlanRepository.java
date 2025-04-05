package io.trishul.classplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.trishul.classplanner.model.ClassPlan;

@Repository
public interface ClassPlanRepository extends JpaRepository<ClassPlan, Long> {
    List<ClassPlan> findByUserId(String userId);
    List<ClassPlan> findByUserIdAndIdIn(String userId, List<Long> ids);
    void deleteByIdAndUserId(Long id, String userId);
}
