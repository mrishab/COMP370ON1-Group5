package io.trishul.classplanner.availability.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO;
import io.trishul.classplanner.availability.model.Availability;

@Mapper(componentModel = "spring", uses = {AvailabilityDayMapper.class})
public interface AvailabilityMapper {
  GetAvailabilityDTO toDTO(Availability availability);

  @Mapping(target = "id", ignore = true)
  Availability toEntity(PostAvailabilityDTO dto);
}
