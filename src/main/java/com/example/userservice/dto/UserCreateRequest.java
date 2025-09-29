package com.example.userservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UserCreateRequest(
        @NotBlank String phone,
        @NotBlank String sex,
        @NotNull UUID photoId,
        @NotNull LocalDate birthdate,
        @NotBlank String email,
        @Valid @NotNull PassportCreateRequest passport
) {}
