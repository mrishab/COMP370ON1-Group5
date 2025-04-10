package io.trishul.classplanner.availability.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import io.trishul.classplanner.availability.controller.dto.GetAvailabilityDTO.GetAvailabilityDayDTO;
import io.trishul.classplanner.availability.controller.dto.PostAvailabilityDTO.PostAvailabilityDayDTO;
import io.trishul.classplanner.availability.model.AvailabilityDay;

@Mapper(componentModel = "spring", uses = {AvailabilityHourMapper.class})
public interface AvailabilityDayMapper {
  GetAvailabilityDayDTO toDTO(AvailabilityDay day);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "availability", ignore = true)
  AvailabilityDay toEntity(PostAvailabilityDayDTO dto);
}
