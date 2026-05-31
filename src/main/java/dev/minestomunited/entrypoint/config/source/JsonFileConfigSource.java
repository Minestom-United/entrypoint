package dev.minestomunited.entrypoint.config.source;

import dev.minestomunited.entrypoint.config.ConfigSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonFileConfigSource implements ConfigSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileConfigSource.class);
    private final Path basePath;

    public JsonFileConfigSource(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public Optional<InputStream> read(String key) {
        if (!key.endsWith(".json")) {
            key = key + ".json";
        }
        Path path = basePath.resolve(key);
        if (Files.exists(path)) {
            try {
                return Optional.of(Files.newInputStream(path));
            } catch (IOException e) {
                LOGGER.error("An error occurred whilst creating an input stream!", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public int priority() {
        return 10;
    }
}
