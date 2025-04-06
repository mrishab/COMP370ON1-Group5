package io.trishul.classplanner.classplan.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classplan.controller.dto.GetClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PostClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PutClassPlanDTO;
import io.trishul.classplanner.classplan.model.ClassPlan;

@Mapper(componentModel = "spring")
public interface ClassPlanMapper {
    @Mapping(target = "gradPlanId", source = "gradPlan.id")
    GetClassPlanDTO toGetDTO(ClassPlan classPlan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gradPlan.id", source = "gradPlanId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", constant = "false")
    ClassPlan toEntity(PostClassPlanDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gradPlan", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(@MappingTarget ClassPlan classPlan, PutClassPlanDTO dto);
}
