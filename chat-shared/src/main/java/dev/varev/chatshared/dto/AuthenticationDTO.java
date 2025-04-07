package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO implements Response {
    private String username;
    private String password;
}
