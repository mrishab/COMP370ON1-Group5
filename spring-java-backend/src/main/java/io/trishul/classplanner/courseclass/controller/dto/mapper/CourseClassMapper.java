package io.trishul.classplanner.courseclass.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.classschedule.controller.dto.mapper.ClassScheduleMapper;
import io.trishul.classplanner.course.controller.dto.mapper.CourseMapper;
import io.trishul.classplanner.courseclass.controller.dto.GetCourseClassDTO;
import io.trishul.classplanner.courseclass.model.CourseClass;

@Mapper(componentModel = "spring", uses = {ClassScheduleMapper.class, CourseMapper.class})
public interface CourseClassMapper {
    GetCourseClassDTO toGetDTO(CourseClass courseClass);
}
