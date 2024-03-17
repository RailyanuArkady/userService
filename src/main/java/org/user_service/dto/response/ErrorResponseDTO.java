package org.user_service.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDTO(int error, String description, LocalDateTime timeStamp) {
}
