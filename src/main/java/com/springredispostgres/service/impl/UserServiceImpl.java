package com.springredispostgres.service.impl;

import com.springredispostgres.common.dto.request.UserRequestDto;
import com.springredispostgres.common.dto.response.UserResponseDto;
import com.springredispostgres.common.mapper.UserMapper;
import com.springredispostgres.entity.User;
import com.springredispostgres.exception.ClientBackendException;
import com.springredispostgres.exception.ErrorCode;
import com.springredispostgres.repository.UserRepository;
import com.springredispostgres.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "users")
    public List<UserResponseDto> getAll() {
        List<User> users = userRepository.findAll();

        return userMapper.toListLanguagesDto(users);
    }

    @Override
    public UserResponseDto getByReference(UUID reference) {
        User user = userRepository.findByReference(reference)
                .orElseThrow(() -> new ClientBackendException(ErrorCode.NOT_FOUND));

        return userMapper.modelToResponse(user);
    }

    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User newUser = userMapper.requestToModel(userRequestDto);
        User savedUser = userRepository.save(newUser);

        return userMapper.modelToResponse(savedUser);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserResponseDto updateByReference(UUID reference, UserRequestDto userRequestDto) {
        User user = userRepository.findByReference(reference)
                .orElseThrow(() -> new ClientBackendException(ErrorCode.NOT_FOUND));
        User updatedUser = userMapper.requestToEntity(userRequestDto, user);

        return userMapper.modelToResponse(updatedUser);
    }

    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void deleteByReference(UUID reference) {
        User user = userRepository.findByReference(reference)
                .orElseThrow(() -> new ClientBackendException(ErrorCode.NOT_FOUND));
        userRepository.findByReference(reference);
    }
}
