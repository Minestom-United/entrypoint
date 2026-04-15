package dev.minestomunited.entrypoint.environment;

import java.util.UUID;

/**
 * Server-wide constants resolved from environment variables at startup.
 */
public class SharedConstants {

    public final static Environment ENVIRONMENT = getFromEnumOrDefault("ENVIRONMENT", Environment.PRODUCTION);
    public final static String PROXIED_SECRET = getEnvOrDefault("VELOCITY_SECRET", "secret");
    public final static String HOSTNAME = getHostName();


    static <T extends Enum<T>> T getFromEnumOrDefault(String key, T defaultValue) {
        return Enum.valueOf(defaultValue.getDeclaringClass(), System.getenv().getOrDefault(key.toUpperCase(), defaultValue.name()));
    }

    static String getEnvOrDefault(String key, String defaultValue) {
        return System.getenv().getOrDefault(key, defaultValue);
    }

    static Integer getEnvOrDefault(String key, Integer defaultValue) {
        return Integer.parseInt(System.getenv().getOrDefault(key, defaultValue.toString()));
    }

    static String getHostName() {
        if (System.getenv("HOSTNAME") != null) {
            return System.getenv("HOSTNAME");
        } else if (ENVIRONMENT.test(Environment.TESTING)) {
            // No HOSTNAME env var in local/test — generate a random identifier
            return "dev-" + UUID.randomUUID().toString().substring(0, 5) + "-" + UUID.randomUUID().toString().substring(0, 5);
        }

        throw new IllegalStateException("Hostname could not be found");
    }

    private SharedConstants() {
        // Prevent instantiation
    }
}
