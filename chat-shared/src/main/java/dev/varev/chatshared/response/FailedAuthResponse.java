package dev.varev.chatshared.response;

import dev.varev.chatshared.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FailedAuthResponse implements Response {
    ErrorDTO error;
    ResponseCode code;

    @Override
    public ResponseCode getCode() {
        return this.code;
    }
}
