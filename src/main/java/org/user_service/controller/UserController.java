package org.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.CreateUserResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("public/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "User and passport post method", description = "Returns user UUID")
    @ApiResponse(description = "User created successfully", responseCode = "201",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateUserResponseDTO.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new CreateUserResponseDTO(userService.saveUser(userRequestDTO));
    }

    @Operation(summary = "Get User by id", description = "Returns user and passport found by id")
    @ApiResponse(description = "User found", responseCode = "200",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponseDTO.class))})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Update User with passed id", description = "Returns updated user and password")
    @ApiResponse(description = "User updated successfully", responseCode = "200",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponseDTO.class))})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserByID(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id,
                                          @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    @Operation(summary = "Soft delete user with passed id")
    @ApiResponse(description = "User deleted successfully", responseCode = "204")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id) {
        userService.deleteUser(id);
    }
}
