package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.classdetail.dto.classentry.GetClassEntryDTO;
import io.trishul.classplanner.classdetail.dto.classentry.PostClassEntryDTO;
import io.trishul.classplanner.classdetail.dto.classentry.PutClassEntryDTO;
import io.trishul.classplanner.classdetail.model.ClassEntry;

@Mapper(componentModel = "spring", uses = {ClassDetailMapper.class})
public interface ClassEntryMapper {
    GetClassEntryDTO toGetDTO(ClassEntry entity);
    
    @Mapping(target = "id", ignore = true)
    ClassEntry toEntity(PostClassEntryDTO dto);
    
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget ClassEntry entity, PutClassEntryDTO dto);
}
