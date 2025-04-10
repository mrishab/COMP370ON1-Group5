package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;
import io.trishul.classplanner.classdetail.dto.classdetail.GetClassDetailDTO;
import io.trishul.classplanner.classdetail.model.ClassDetail;
import io.trishul.classplanner.classschedule.controller.dto.mapper.ClassScheduleMapper;

@Mapper(componentModel = "spring", uses = {ClassScheduleMapper.class})
public interface ClassDetailMapper {
  GetClassDetailDTO toGetDTO(ClassDetail entity);
}
