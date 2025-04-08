package dev.varev.chatshared.request;

public class ListChannelsRequest implements Request {
    @Override
    public RequestType getType() {
        return RequestType.PUBLIC_CHANNELS;
    }
}
