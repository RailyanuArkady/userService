package com.example.userservice.dto.request;

import com.example.userservice.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record UserUpdateRequest(
        @NotBlank
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Invalid phone format")
        String phone,
        @NotNull
        Sex sex,
        @NotNull
        UUID photoId,
        @NotNull
        LocalDate birthdate
) {
}
