package com.example.userservice.controller;

import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.service.PassportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/public/api/v1/users/{userId}/passport")
@RequiredArgsConstructor
public class PassportController {
    private final PassportService passportService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updatePassport(
            @PathVariable("userId") UUID externalId,
            @Valid @RequestBody PassportUpdateRequest request
    ) {
        return passportService.updatePassport(externalId, request);
    }
}
