package org.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.user_service.dto.UserResponseDto;
import org.user_service.dto.UserDTO;
import org.user_service.services.UserServiceClass;

import java.util.UUID;

@RestController
@RequestMapping("public/api/v1/users")
public class UserController {

    private final UserServiceClass userServiceClass;

    public UserController(UserServiceClass userServiceClass) {
        this.userServiceClass = userServiceClass;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserDTO userDTO) {
        UUID uuid = userServiceClass.save(userDTO);
        UserResponseDto response = new UserResponseDto(uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
