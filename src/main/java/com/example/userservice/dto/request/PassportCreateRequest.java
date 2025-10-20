package com.example.userservice.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PassportCreateRequest(
        @Valid
        @NotNull
        PassportData passport
) {
}