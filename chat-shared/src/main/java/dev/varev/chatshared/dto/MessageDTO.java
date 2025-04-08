package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Response {
    private Instant createdAt;
    private String content;

    @Override
    public String toString() {
        return "[" + createdAt + "] " + content;
    }
}
