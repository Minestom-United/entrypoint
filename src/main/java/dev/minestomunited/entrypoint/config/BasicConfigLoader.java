package dev.minestomunited.entrypoint.config;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Basic {@link ConfigLoader} implementation provided by the entrypoint library.
 *
 * <p>Sources are applied in ascending priority order — the highest-priority source
 * that provides data for a given config key wins (whole-object replacement, no merging).
 *
 * <p>Registered via {@link java.util.ServiceLoader}.
 */
public class BasicConfigLoader implements ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicConfigLoader.class);

    private @Nullable ConfigFormat format;
    private final List<ConfigSource> sources = new ArrayList<>();
    private final Map<Class<? extends Config>, Config> defaults = new HashMap<>();
    private final Map<Class<? extends Config>, Config> loaded = new HashMap<>();

    @Override
    public ConfigLoader withFormat(ConfigFormat format) {
        this.format = format;
        return this;
    }

    @Override
    public ConfigLoader addSource(ConfigSource source) {
        sources.add(source);
        sources.sort(Comparator.comparingInt(ConfigSource::priority));
        return this;
    }

    @Override
    public <C extends Config> ConfigLoader register(Class<C> clazz, C defaultInstance) {
        defaults.put(clazz, defaultInstance);
        return this;
    }

    @Override
    public ConfigLoader initialize(String[] args) {
        if (format == null) {
            throw new IllegalStateException("No ConfigFormat set — call withFormat() before initialize()");
        }

        for (Map.Entry<Class<? extends Config>, Config> entry : defaults.entrySet()) {
            Class<? extends Config> clazz = entry.getKey();
            Config resolved = entry.getValue();

            String key = resolveKey(clazz);

            // Apply sources in ascending priority — last non-empty result wins
            for (ConfigSource source : sources) {
                Optional<InputStream> data = source.read(key);
                if (data.isEmpty()) {
                    continue;
                }
                try (InputStream in = data.get()) {
                    resolved = format.deserialize(clazz, in);
                    LOGGER.debug("Loaded {} from {} (priority {})", clazz.getSimpleName(), source.getClass().getSimpleName(), source.priority());
                } catch (IOException e) {
                    LOGGER.warn("Failed to load {} from {} — skipping source", clazz.getSimpleName(), source.getClass().getSimpleName(), e);
                }
            }

            loaded.put(clazz, resolved);
            LOGGER.info("Config {} resolved", clazz.getSimpleName());
        }

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Config> Optional<C> get(Class<C> clazz) {
        C config = (C) loaded.get(clazz);
        return Optional.ofNullable(config);
    }

    private static String resolveKey(Class<? extends Config> clazz) {
        ConfigFile annotation = clazz.getAnnotation(ConfigFile.class);
        return annotation != null ? annotation.value() : clazz.getSimpleName() + ".yml";
    }
}
