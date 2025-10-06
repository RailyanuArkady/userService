package com.example.userservice.controller;

import com.example.userservice.dto.*;
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

    @GetMapping("/{externalUserId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable UUID externalUserId) {
        return userService.getUserByExternalId(externalUserId);
    }

    @PutMapping("/{externalId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@PathVariable UUID externalId,
                                   @Valid @RequestBody UserUpdateRequest request
    ) {
        return userService.updateUser(externalId, request);
    }

    @PutMapping("/{externalId}/passport")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updatePassport(@PathVariable UUID externalId,
                                       @Valid @RequestBody PassportUpdateRequest request
    ) {
        return userService.updatePassport(externalId, request);
    }

}
