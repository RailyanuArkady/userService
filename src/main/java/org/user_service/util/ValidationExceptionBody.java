package org.user_service.util;

import java.time.LocalDateTime;

public record ValidationExceptionBody(int error, String description, LocalDateTime timeStamp) {
}
