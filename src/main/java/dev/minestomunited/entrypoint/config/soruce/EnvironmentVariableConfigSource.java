package dev.minestomunited.entrypoint.config.soruce;

import dev.minestomunited.entrypoint.config.ConfigSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import org.jspecify.annotations.NonNull;

public final class EnvironmentVariableConfigSource implements ConfigSource {

    private static final String ENVIRONMENT_PREFIX = "MINESTOM_";

    @Override
    public @NonNull Optional<InputStream> read(String key) {
        String value = System.getenv(ENVIRONMENT_PREFIX.concat(key.toUpperCase().replace('.', '_')));

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(new ByteArrayInputStream(value.getBytes()));
    }

    @Override
    public int priority() {
        return 67;
    }
}
