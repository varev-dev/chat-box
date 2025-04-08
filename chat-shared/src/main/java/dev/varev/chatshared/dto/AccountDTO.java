package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;

@Value
@AllArgsConstructor
public class AccountDTO implements Response {
    String username;
    Instant createdAt;
    Instant lastLogin;
}
