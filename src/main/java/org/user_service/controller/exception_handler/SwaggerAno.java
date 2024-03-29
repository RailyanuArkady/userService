package org.user_service.controller.exception_handler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.CreateUserResponseDTO;
import org.user_service.dto.response.UserResponseDTO;

import java.util.UUID;

public interface SwaggerAno {
    @Operation(summary = "User and passport post method", description = "Returns user UUID")
    @ApiResponse(description = "User created successfully", responseCode = "201",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateUserResponseDTO.class))})
    CreateUserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO);

    @Operation(summary = "Get User by id", description = "Returns user and passport found by id")
    @ApiResponse(description = "User found", responseCode = "200",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponseDTO.class))})
    UserResponseDTO getUserById(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id);

    @Operation(summary = "Update User with passed id", description = "Returns updated user and password")
    @ApiResponse(description = "User updated successfully", responseCode = "200",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponseDTO.class))})
    UserResponseDTO updateUserByID(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id,
                                   @Valid @RequestBody UserRequestDTO userRequestDTO);

    @Operation(summary = "Soft delete user with passed id")
    @ApiResponse(description = "User deleted successfully", responseCode = "204")
    void deleteUser(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id);
}
