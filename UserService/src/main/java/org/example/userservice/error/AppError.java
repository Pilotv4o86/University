package org.example.userservice.error;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppError
        (
                int status,

                String message,

                LocalDateTime timestamp

        ) {
}