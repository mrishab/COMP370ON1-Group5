package io.trishul.classplanner.classdetail.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.classdetail.model.ClassDetail;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface ClassDetailRepository extends BaseRepository<ClassDetail, Long> {
}
