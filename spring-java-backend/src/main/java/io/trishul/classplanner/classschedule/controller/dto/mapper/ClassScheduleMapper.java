package io.trishul.classplanner.classschedule.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PostClassScheduleDTO;
import io.trishul.classplanner.classschedule.controller.dto.PutClassScheduleDTO;
import io.trishul.classplanner.classschedule.model.ClassSchedule;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
    GetClassScheduleDTO toGetDTO(ClassSchedule classSchedule);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", constant = "false")
    ClassSchedule toEntity(PostClassScheduleDTO postClassScheduleDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(@MappingTarget ClassSchedule classSchedule, PutClassScheduleDTO putClassScheduleDTO);
}
