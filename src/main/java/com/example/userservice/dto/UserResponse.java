package com.example.userservice.dto;

import com.example.userservice.enums.Sex;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        String phone,
        Sex sex,
        UUID photoId,
        LocalDate birthdate,
        PassportResponse passport
) {
}
