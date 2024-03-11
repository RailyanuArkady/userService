package org.user_service.util;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ValidationExceptionBody {
    private final int error;
    private final String description;
    private final Instant timeStamp;

    public ValidationExceptionBody(int error, String description, Instant timeStamp) {
        this.error = error;
        this.description = description;
        this.timeStamp = timeStamp;
    }
}
