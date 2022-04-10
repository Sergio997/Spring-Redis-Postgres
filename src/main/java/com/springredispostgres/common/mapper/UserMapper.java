package com.springredispostgres.common.mapper;

import com.springredispostgres.common.dto.request.UserRequestDto;
import com.springredispostgres.common.dto.response.UserResponseDto;
import com.springredispostgres.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto modelToResponse(User user);

    User requestToModel(UserRequestDto request);

    User requestToEntity(UserRequestDto request, @MappingTarget User user);

    List<UserResponseDto> toListLanguagesDto(List<User> users);

}
