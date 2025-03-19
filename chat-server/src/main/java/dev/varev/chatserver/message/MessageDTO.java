package dev.varev.chatserver.message;

import java.time.Instant;

public record MessageDTO(Instant createdAt, String content) {
}
