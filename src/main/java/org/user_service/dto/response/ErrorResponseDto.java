package org.user_service.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDto(int error, String description, LocalDateTime timeStamp) {
}
