package io.trishul.classplanner.classplan.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import io.trishul.classplanner.availability.controller.dto.mapper.AvailabilityMapper;
import io.trishul.classplanner.classdetail.controller.dto.mapper.CourseMapper;
import io.trishul.classplanner.classplan.controller.dto.GetClassPlanDTO;
import io.trishul.classplanner.classplan.controller.dto.PostClassPlanDTO;
import io.trishul.classplanner.classplan.model.ClassPlan;
import io.trishul.classplanner.gradplan.controller.dto.mapper.GradPlanMapper;

@Mapper(componentModel = "spring",
    uses = {AvailabilityMapper.class, CourseMapper.class, GradPlanMapper.class})
public interface ClassPlanMapper {
  GetClassPlanDTO toGetDTO(ClassPlan classPlan);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "gradPlan.id", source = "gradPlanId")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "archived", constant = "false")
  @Mapping(target = "classes", ignore = true)
  ClassPlan toEntity(PostClassPlanDTO dto);
}
