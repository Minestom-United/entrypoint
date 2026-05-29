package dev.minestomunited.entrypoint.config.source;

import dev.minestomunited.entrypoint.config.ConfigSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

public final class EnvironmentVariableConfigSource implements ConfigSource {
    private final String prefix;

    public EnvironmentVariableConfigSource(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Optional<InputStream> read(String key) {
        String value = System.getenv(prefix.concat(key.toUpperCase().replace('.', '_')));

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(new ByteArrayInputStream(value.getBytes()));
    }

    @Override
    public int priority() {
        return 10;
    }
}
