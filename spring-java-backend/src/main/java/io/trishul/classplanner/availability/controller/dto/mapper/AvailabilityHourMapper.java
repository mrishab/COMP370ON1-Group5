package io.trishul.classplanner.availability.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO.GetAvailabilityHourDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO.PostAvailabilityHourDTO;
import io.trishul.classplanner.availability.controller.dto.PutAvailabilityDTO.PutAvailabilityHourDTO;
import io.trishul.classplanner.availability.model.AvailabilityHour;

@Mapper(componentModel = "spring")
public interface AvailabilityHourMapper {
    GetAvailabilityHourDTO toDTO(AvailabilityHour hour);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availabilityDay", ignore = true)
    AvailabilityHour toEntity(PostAvailabilityHourDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availabilityDay", ignore = true)
    AvailabilityHour toEntity(PutAvailabilityHourDTO dto);
}
