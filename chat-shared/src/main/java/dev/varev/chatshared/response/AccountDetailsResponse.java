package dev.varev.chatshared.response;

import dev.varev.chatshared.dto.AccountDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDetailsResponse implements Response {
    AccountDetailsDTO details;

    @Override
    public ResponseCode getCode() {
        return ResponseCode.OK;
    }
}
