package io.trishul.classplanner.gradplan.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.gradplan.controller.dto.GetGradPlanDTO;
import io.trishul.classplanner.gradplan.model.GradPlan;

@Mapper(componentModel = "spring")
public interface GradPlanMapper {
    GetGradPlanDTO toGetDTO(GradPlan gradPlan);
}
