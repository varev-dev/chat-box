package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AuthenticationDTO implements Response {
    String username;
    String password;
}
