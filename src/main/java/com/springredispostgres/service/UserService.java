package com.springredispostgres.service;

import com.springredispostgres.common.dto.request.UserRequestDto;
import com.springredispostgres.common.dto.response.UserResponseDto;

import java.util.UUID;
import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getByReference(UUID reference);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto updateByReference(UUID reference, UserRequestDto userRequestDto);

    void deleteByReference(UUID reference);
}
