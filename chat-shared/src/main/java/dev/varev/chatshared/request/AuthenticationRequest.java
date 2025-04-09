package dev.varev.chatshared.request;

import dev.varev.chatshared.dto.AuthenticationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class AuthenticationRequest implements Request  {
    AuthenticationDTO auth;

    @Override
    public RequestType getType() {
        return RequestType.AUTHENTICATE;
    }
}
