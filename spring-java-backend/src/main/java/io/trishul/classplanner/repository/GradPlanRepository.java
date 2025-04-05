package io.trishul.classplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.trishul.classplanner.model.GradPlan;

@Repository
public interface GradPlanRepository extends JpaRepository<GradPlan, Long> {
    List<GradPlan> findByUserId(String userId);
    void deleteByIdAndUserId(Long id, String userId);
}
