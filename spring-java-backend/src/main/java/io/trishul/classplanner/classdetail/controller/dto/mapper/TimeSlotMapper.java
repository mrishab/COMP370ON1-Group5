package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classdetail.dto.timeslot.GetTimeSlotDTO;
import io.trishul.classplanner.classdetail.dto.timeslot.PostTimeSlotDTO;
import io.trishul.classplanner.classdetail.dto.timeslot.PutTimeSlotDTO;
import io.trishul.classplanner.classdetail.model.TimeSlot;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {
    GetTimeSlotDTO toGetDTO(TimeSlot entity);
    
    @Mapping(target = "id", ignore = true)
    TimeSlot toEntity(PostTimeSlotDTO dto);
    
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget TimeSlot entity, PutTimeSlotDTO dto);
}
