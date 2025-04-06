package io.trishul.classplanner.course.repository;

import org.springframework.stereotype.Repository;

import io.trishul.classplanner.common.repository.BaseRepository;
import io.trishul.classplanner.course.model.Course;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {
}
