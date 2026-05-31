package dev.minestomunited.entrypoint.config.source;

import dev.minestomunited.entrypoint.config.ConfigSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

public final class EnvironmentVariableConfigSource implements ConfigSource {
    private static final String ENVIRONMENT_PREFIX = "MINESTOM_";

    public Optional<InputStream> read(String key) {
        String value = System.getenv(ENVIRONMENT_PREFIX + key.toUpperCase().replace('.', '_'));

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(new ByteArrayInputStream(value.getBytes()));
    }

    @Override
    public int priority() {
        return 100;
    }
}
