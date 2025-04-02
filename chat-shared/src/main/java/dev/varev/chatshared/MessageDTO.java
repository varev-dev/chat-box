package dev.varev.chatshared;

import java.time.Instant;

public record MessageDTO(Instant createdAt, String content) {
    @Override
    public String toString() {
        return "[" + createdAt + "] " + content;
    }
}
