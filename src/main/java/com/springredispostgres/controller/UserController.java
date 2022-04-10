package com.springredispostgres.controller;

import com.springredispostgres.common.dto.request.UserRequestDto;
import com.springredispostgres.common.dto.response.UserResponseDto;
import com.springredispostgres.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{reference}")
    @Operation(summary = "Get User by Reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User by Reference")
    })
    public UserResponseDto getByReference(@PathVariable UUID reference){
        return userService.getByReference(reference);
    }

    @GetMapping
    @Operation(summary = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Users")
    })
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create Use")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create User")
    })
    public UserResponseDto create(@RequestBody UserRequestDto userRequestDto) {
        return userService.create(userRequestDto);
    }

    @PutMapping("/{reference}")
    @Operation(summary = "Update User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Users")
    })
    public UserResponseDto updateByReference(@PathVariable UUID reference, @RequestBody UserRequestDto userRequestDto){
        return userService.updateByReference(reference, userRequestDto);
    }

    @DeleteMapping("/{reference}")
    @Operation(summary = "Delete User by Reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete User by Reference")
    })
    public void deleteByReference(@PathVariable UUID reference){
        userService.deleteByReference(reference);
    }

}
