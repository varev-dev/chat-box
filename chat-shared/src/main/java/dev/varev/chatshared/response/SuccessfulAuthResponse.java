package dev.varev.chatshared.response;

import dev.varev.chatshared.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessfulAuthResponse implements Response {
    AccountDTO account;

    @Override
    public ResponseCode getCode() {
        return ResponseCode.OK;
    }
}
