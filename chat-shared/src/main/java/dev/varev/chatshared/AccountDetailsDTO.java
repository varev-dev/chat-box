package dev.varev.chatshared;


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
