package dev.varev.chatshared.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class ErrorDTO implements Serializable {
    ErrorCode code;
    String message;
}
