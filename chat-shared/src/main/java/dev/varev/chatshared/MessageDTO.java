package dev.varev.chatshared;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements ResponseDTO {
    private Instant createdAt;
    private String content;

    @Override
    public String toString() {
        return "[" + createdAt + "] " + content;
    }
}
