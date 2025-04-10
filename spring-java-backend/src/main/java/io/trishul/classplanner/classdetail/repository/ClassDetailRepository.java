package io.trishul.classplanner.classdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.ClassDetail;

@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long> {
}
