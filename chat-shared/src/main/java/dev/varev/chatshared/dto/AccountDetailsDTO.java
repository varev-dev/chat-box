package dev.varev.chatshared.dto;


import dev.varev.chatshared.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDTO implements Response {
    private AccountDTO account;
    private List<ChannelDTO> channels;
}
