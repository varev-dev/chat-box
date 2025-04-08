package dev.varev.chatshared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExitResponse implements Response {
    boolean success;
}
