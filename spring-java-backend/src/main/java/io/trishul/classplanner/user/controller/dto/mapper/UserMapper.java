package io.trishul.classplanner.user.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import io.trishul.classplanner.user.controller.dto.GetUserDTO;
import io.trishul.classplanner.user.controller.dto.PostUserDTO;
import io.trishul.classplanner.user.controller.dto.PutUserDTO;
import io.trishul.classplanner.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  GetUserDTO toGetDTO(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "archived", constant = "false")
  User toEntity(PostUserDTO dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "archived", ignore = true)
  void updateEntity(@MappingTarget User user, PutUserDTO dto);
}
