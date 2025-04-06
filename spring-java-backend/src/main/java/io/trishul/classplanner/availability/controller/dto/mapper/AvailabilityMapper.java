package io.trishul.classplanner.availability.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.trishul.classplanner.availability.model.Availability;
import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PutAvailabilityDTO;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {
    GetAvailabilityDTO toDTO(Availability availability);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    Availability toEntity(PostAvailabilityDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    Availability toEntity(PutAvailabilityDTO dto);
}
