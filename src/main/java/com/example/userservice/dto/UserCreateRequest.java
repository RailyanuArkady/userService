package com.example.userservice.dto;

import com.example.userservice.enums.Sex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record UserCreateRequest(
        @NotBlank
        @Pattern(regexp = "somePhone")
        String phone,
        @NotBlank
        Sex sex,
        @NotNull
        UUID photoId,
        @NotNull
        LocalDate birthdate,
        @Email
        @NotBlank
        String email,
        @Valid
        @NotNull
        PassportCreateRequest passport
) {
}
