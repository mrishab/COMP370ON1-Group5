package io.trishul.classplanner.courseclass.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.courseclass.controller.dto.GetCourseClassDTO;
import io.trishul.classplanner.courseclass.controller.dto.PostCourseClassDTO;
import io.trishul.classplanner.courseclass.controller.dto.PutCourseClassDTO;
import io.trishul.classplanner.courseclass.model.CourseClass;

@Mapper(componentModel = "spring")
public interface CourseClassMapper {
    @Mapping(target = "courseId", source = "course.id")
    GetCourseClassDTO toGetDTO(CourseClass courseClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", constant = "false")
    CourseClass toEntity(PostCourseClassDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(@MappingTarget CourseClass courseClass, PutCourseClassDTO dto);
}
