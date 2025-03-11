package dev.varev.chatserver.channel;

import java.util.HashSet;
import java.util.Set;

public class ChannelRepository {
    private final Set<Channel> channels;

    public ChannelRepository() {
        this.channels = new HashSet<>();
    }

    public ChannelRepository(Set<Channel> channels) {
        this.channels = channels;
    }
}
