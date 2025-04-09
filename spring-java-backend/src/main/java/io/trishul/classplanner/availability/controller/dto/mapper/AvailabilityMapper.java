package io.trishul.classplanner.availability.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PutAvailabilityDTO;
import io.trishul.classplanner.availability.model.Availability;

@Mapper(componentModel = "spring", uses = {AvailabilityDayMapper.class})
public interface AvailabilityMapper {
    GetAvailabilityDTO toDTO(Availability availability);
    
    Availability toEntity(PostAvailabilityDTO dto);
    
    Availability toEntity(PutAvailabilityDTO dto);
}
