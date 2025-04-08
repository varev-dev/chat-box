package dev.varev.chatshared.dto;


import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class AccountDetailsDTO implements Response {
    AccountDTO account;
    List<ChannelDTO> channels;
}
