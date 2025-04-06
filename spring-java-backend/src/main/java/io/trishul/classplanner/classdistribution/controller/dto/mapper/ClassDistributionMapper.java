package io.trishul.classplanner.classdistribution.controller.dto.mapper;

import io.trishul.classplanner.classdistribution.controller.dto.GetClassDistributionDTO;
import io.trishul.classplanner.classdistribution.controller.dto.PostClassDistributionDTO;
import io.trishul.classplanner.classdistribution.controller.dto.PutClassDistributionDTO;
import io.trishul.classplanner.classdistribution.model.ClassDistribution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClassDistributionMapper {
    GetClassDistributionDTO toGetDTO(ClassDistribution entity);
    ClassDistribution toEntity(PostClassDistributionDTO dto);
    void updateEntity(@MappingTarget ClassDistribution entity, PutClassDistributionDTO dto);
}
