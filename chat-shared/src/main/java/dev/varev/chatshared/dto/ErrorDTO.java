package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import dev.varev.chatshared.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO implements Response {
    private ResponseCode code;
    private String message;
}
