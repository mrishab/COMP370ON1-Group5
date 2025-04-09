package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classdetail.dto.classdetail.GetClassDetailDTO;
import io.trishul.classplanner.classdetail.dto.classdetail.PostClassDetailDTO;
import io.trishul.classplanner.classdetail.dto.classdetail.PutClassDetailDTO;
import io.trishul.classplanner.classdetail.model.ClassDetail;

@Mapper(componentModel = "spring", uses = {TimeSlotMapper.class})
public interface ClassDetailMapper {
    GetClassDetailDTO toGetDTO(ClassDetail entity);
    
    @Mapping(target = "id", ignore = true)
    ClassDetail toEntity(PostClassDetailDTO dto);
    
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget ClassDetail entity, PutClassDetailDTO dto);
}
