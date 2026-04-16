package dev.minestomunited.entrypoint.config.soruce;

import dev.minestomunited.entrypoint.config.ConfigSource;
import org.jspecify.annotations.NonNull;

import java.io.InputStream;
import java.util.Optional;

public final class EnvironmentVariableConfigSource implements ConfigSource {

    private final static String ENVIRONMENT_PREFIX = "MINESTOM_";

    @Override
    public @NonNull Optional<InputStream> read(String key) {
        String value = System.getenv(ENVIRONMENT_PREFIX.concat(key.toUpperCase().replace('.', '_')));

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(new java.io.ByteArrayInputStream(value.getBytes()));
    }

    @Override
    public int priority() {
        return 67;
    }
}
