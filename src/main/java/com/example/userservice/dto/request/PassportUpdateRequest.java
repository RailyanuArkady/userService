package com.example.userservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PassportUpdateRequest(
        @Valid
        @NotNull
        PassportData passport
) {
}
