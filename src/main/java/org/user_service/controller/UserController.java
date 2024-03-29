package org.user_service.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.user_service.controller.exception_handler.SwaggerAno;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.CreateUserResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("public/api/v1/users")
@RequiredArgsConstructor
public class UserController implements SwaggerAno {

    private final UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new CreateUserResponseDTO(userService.saveUser(userRequestDTO));
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id) {
        return userService.findUserById(id);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserByID(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id,
                                          @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Parameter(schema = @Schema(implementation = UUID.class)) Long id) {
        userService.deleteUser(id);
    }
}
