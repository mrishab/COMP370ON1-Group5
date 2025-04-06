package io.trishul.classplanner.classschedule.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PostClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PutClassScheduleDTO;
import io.trishul.classplanner.classschedule.model.ClassSchedule;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
    GetClassScheduleDTO toGetDTO(ClassSchedule classSchedule);
    ClassSchedule toEntity(PostClassScheduleDTO postClassScheduleDTO);
    void updateEntity(@MappingTarget ClassSchedule classSchedule, PutClassScheduleDTO putClassScheduleDTO);
}
