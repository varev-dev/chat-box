package dev.varev.chatshared.dto;

import java.io.Serializable;

public enum ErrorCode implements Serializable {
    NOT_FOUND,
    UNAUTHORIZED,
    INTERNAL,
    FORBIDDEN
}
