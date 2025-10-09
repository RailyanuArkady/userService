package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserCreateResponse;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserUpdateRequest;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/{externalId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable UUID externalId) {
        return userService.getUserByExternalId(externalId);
    }

    @PutMapping("/{externalId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@PathVariable UUID externalId,
                                   @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(externalId, request);
    }

    @DeleteMapping("/{externalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID externalId) {
        userService.deleteUser(externalId);


    }


}
