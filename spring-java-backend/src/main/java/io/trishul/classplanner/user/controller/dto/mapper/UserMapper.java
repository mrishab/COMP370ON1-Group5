package io.trishul.classplanner.user.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import io.trishul.classplanner.user.controller.dto.GetUserDTO;
import io.trishul.classplanner.user.controller.dto.PostUserDTO;
import io.trishul.classplanner.user.controller.dto.PutUserDTO;
import io.trishul.classplanner.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    GetUserDTO toGetDTO(User user);
    User toEntity(PostUserDTO dto);
    void updateEntity(@MappingTarget User user, PutUserDTO dto);
}
