package dev.varev.chatshared.request;


public class ExitRequest implements Request {
    @Override
    public RequestType getType() {
        return RequestType.EXIT;
    }
}
