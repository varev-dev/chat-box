package dev.varev.chatshared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null)
                throw new RuntimeException("Unable to find application.properties.");
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading application.properties", e);
        }
    }

    public static String getServerHost() {
        return properties.getProperty("server.host");
    }

    public static int getServerPort() {
        return Integer.parseInt(properties.getProperty("server.port"));
    }
}
