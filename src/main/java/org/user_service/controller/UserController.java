package org.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.CreateUserResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.services.UserService;

@RestController
@RequestMapping("public/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "User and passport post method", description = "Returns user UUID")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new CreateUserResponseDTO(userService.saveUser(userRequestDTO));
    }

    @Operation(summary = "Get User by id", description = "Returns user and passport found by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Update User with passed id", description = "Returns updated user and password")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserByID(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    @Operation(summary = "Soft delete user with passed id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@PathVariable Long id){
        userService.deleteUser(id);
    }
}
