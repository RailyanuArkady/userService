package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserCreateResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        return new UserCreateResponse(userService.createUser(request));
    }
}