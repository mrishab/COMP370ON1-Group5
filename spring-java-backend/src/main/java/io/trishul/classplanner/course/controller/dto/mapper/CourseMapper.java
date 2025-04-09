package io.trishul.classplanner.course.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.course.controller.dto.GetCourseDTO;
import io.trishul.classplanner.course.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    GetCourseDTO toGetDTO(Course course);
}
