package io.trishul.classplanner.classplan.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classplan.controller.dto.GetBurdenCapacityDTO;
import io.trishul.classplanner.classplan.controller.dto.PostBurdenCapacityDTO;
import io.trishul.classplanner.classplan.controller.dto.PutBurdenCapacityDTO;
import io.trishul.classplanner.classplan.model.BurdenCapacity;

@Mapper(componentModel = "spring")
public interface BurdenCapacityMapper {
    GetBurdenCapacityDTO toGetDTO(BurdenCapacity entity);
    BurdenCapacity toEntity(PostBurdenCapacityDTO dto);
    void updateEntity(@MappingTarget BurdenCapacity entity, PutBurdenCapacityDTO dto);
}
