package com.example.userservice.dto.request;

import com.example.userservice.enums.Sex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

public record UserCreateRequest(
        @NotBlank
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Invalid phone format")
        String phone,
        @NotNull
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
