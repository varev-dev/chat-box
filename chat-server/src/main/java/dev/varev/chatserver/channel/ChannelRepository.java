package dev.varev.chatserver.channel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ChannelRepository {
    private final Set<Channel> channels;

    public ChannelRepository() {
        this.channels = new HashSet<>();
    }

    public ChannelRepository(Set<Channel> channels) {
        this.channels = channels;
    }

    public Optional<Channel> getChannelWithName(String name) {
        return channels.stream()
                .filter(channel -> channel.getName().equals(name))
                .findFirst();
    }

    public boolean addChannel(Channel channel) {
        return channels.add(channel);
    }
}
