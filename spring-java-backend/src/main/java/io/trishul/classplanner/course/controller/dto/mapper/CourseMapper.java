package io.trishul.classplanner.course.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.course.controller.dto.GetCourseDTO;
import io.trishul.classplanner.course.controller.dto.PostCourseDTO;
import io.trishul.classplanner.course.controller.dto.PutCourseDTO;
import io.trishul.classplanner.course.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    GetCourseDTO toGetDTO(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", constant = "false")
    Course toEntity(PostCourseDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(@MappingTarget Course course, PutCourseDTO dto);
}
