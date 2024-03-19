package org.user_service.controller;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new CreateUserResponseDTO(userService.saveUser(userRequestDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserByID(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@PathVariable Long id){
        userService.deleteUser(id);
    }
}
