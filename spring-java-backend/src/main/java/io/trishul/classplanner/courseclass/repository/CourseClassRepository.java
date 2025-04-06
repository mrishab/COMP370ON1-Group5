package io.trishul.classplanner.courseclass.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.courseclass.model.CourseClass;
import io.trishul.classplanner.common.repository.BaseRepository;

@Repository
public interface CourseClassRepository extends BaseRepository<CourseClass, Long> {
}
