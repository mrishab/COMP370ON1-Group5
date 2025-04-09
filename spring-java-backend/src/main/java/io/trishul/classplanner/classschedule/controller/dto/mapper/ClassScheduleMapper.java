package io.trishul.classplanner.classschedule.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import io.trishul.classplanner.classschedule.model.ClassSchedule;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
    GetClassScheduleDTO toGetDTO(ClassSchedule classSchedule);
}
