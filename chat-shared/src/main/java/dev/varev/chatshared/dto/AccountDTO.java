package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Response {
    private String username;
    private Instant createdAt;
    private Instant lastLogin;
}
