package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.classdetail.dto.course.GetCourseDTO;
import io.trishul.classplanner.classdetail.model.Course;

@Mapper(componentModel = "spring", uses = {ClassDetailMapper.class})
public interface CourseMapper {
    GetCourseDTO toGetDTO(Course entity);
}
