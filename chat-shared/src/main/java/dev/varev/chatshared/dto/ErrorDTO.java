package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import dev.varev.chatshared.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorDTO implements Response {
    ResponseCode code;
    String message;
}
