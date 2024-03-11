package org.user_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserResponseDto {
    public UUID id;
    public UserResponseDto(UUID id) {
        this.id = id;
    }
}
