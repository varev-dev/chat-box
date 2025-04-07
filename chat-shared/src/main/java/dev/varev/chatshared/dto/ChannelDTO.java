package dev.varev.chatshared.dto;

import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDTO implements Response {
    private String name;
    private Instant createdAt;
}
