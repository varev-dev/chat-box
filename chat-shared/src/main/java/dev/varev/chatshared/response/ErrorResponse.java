package dev.varev.chatshared.response;

import dev.varev.chatshared.dto.ErrorCode;
import dev.varev.chatshared.dto.ErrorDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse implements Response {
    private final ErrorDTO error;
    private final ResponseCode code;

    @Override
    public ResponseCode getCode() {
        return this.code;
    }
}
