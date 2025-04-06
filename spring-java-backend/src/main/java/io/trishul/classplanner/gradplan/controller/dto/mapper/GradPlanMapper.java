package io.trishul.classplanner.gradplan.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.gradplan.controller.dto.GetGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.PostGradPlanDTO;
import io.trishul.classplanner.gradplan.controller.dto.PutGradPlanDTO;
import io.trishul.classplanner.gradplan.model.GradPlan;

@Mapper(componentModel = "spring")
public interface GradPlanMapper {
    @Mapping(target = "userId", source = "user.id")
    GetGradPlanDTO toGetDTO(GradPlan gradPlan);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "auditedAt", ignore = true)
    @Mapping(target = "archived", constant = "false")
    GradPlan toEntity(PostGradPlanDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "auditedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(@MappingTarget GradPlan gradPlan, PutGradPlanDTO dto);
}
