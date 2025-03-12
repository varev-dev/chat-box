package dev.varev.chatserver.account;

import java.time.Duration;
import java.util.regex.Pattern;

public class AccountConstants {
    public static final int MINIMAL_USERNAME_LENGTH = 3;
    public static final int MINIMAL_PASSWORD_LENGTH = 3;

    private static final String USERNAME_FORMAT = String.format("[a-zA-Z0-9@#$&+=~_-]{%d,}", MINIMAL_USERNAME_LENGTH);
    public static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_FORMAT);

    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    private AccountConstants() {}
}
