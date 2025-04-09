package io.trishul.classplanner.classdetail.controller.dto.mapper;

import org.mapstruct.Mapper;

import io.trishul.classplanner.classdetail.dto.classentry.GetClassEntryDTO;
import io.trishul.classplanner.classdetail.model.ClassEntry;

@Mapper(componentModel = "spring", uses = {ClassDetailMapper.class})
public interface ClassEntryMapper {
    GetClassEntryDTO toGetDTO(ClassEntry entity);
}
