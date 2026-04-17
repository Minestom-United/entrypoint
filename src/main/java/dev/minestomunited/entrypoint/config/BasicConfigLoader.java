package dev.minestomunited.entrypoint.config;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Basic {@link ConfigLoader} implementation provided by the entrypoint library.
 *
 * <p>Sources are applied in ascending priority order — the highest-priority source
 * that provides data for a given config key wins (whole-object replacement, no merging).
 *
 * <p>Once {@link #initialize(String[])} has been called, subsequent calls to
 * {@link #register(Class, Config)} will load the config immediately rather than
 * deferring to a future initialize call.
 *
 * <p>Registered via {@link java.util.ServiceLoader}.
 */
public class BasicConfigLoader implements ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicConfigLoader.class);

    private @Nullable ConfigFormat format;
    private final List<ConfigSource> sources = new ArrayList<>();
    private final Map<Class<? extends Config>, @Nullable Config> defaults = new HashMap<>();
    private final Map<Class<? extends Config>, @Nullable Config> loaded = new HashMap<>();
    private boolean initialized = false;

    @Override
    public ConfigLoader withFormat(ConfigFormat format) {
        this.format = format;
        if (initialized) {
            LOGGER.debug("ConfigFormat changed post-init — reloading all configs");
            for (Map.Entry<Class<? extends Config>, @Nullable Config> entry : defaults.entrySet()) {
                //noinspection unchecked
                loadConfig(entry.getKey(), entry.getValue(), format);
            }
        }
        return this;
    }

    @Override
    public ConfigLoader addSource(ConfigSource source) {
        sources.add(source);
        sources.sort(Comparator.comparingInt(ConfigSource::priority));
        return this;
    }

    @Override
    public <C extends Config> ConfigLoader register(Class<C> clazz, @Nullable C defaultInstance) {
        defaults.put(clazz, defaultInstance);
        if (initialized) {
            loadConfig(clazz, defaultInstance);
        }
        return this;
    }

    @Override
    public ConfigLoader initialize(String[] args) {
        if (initialized) {
            throw new IllegalStateException("ConfigLoader already initialized — register() new configs after initialize() instead");
        }
        ConfigFormat fmt = (format != null) ? format : new NoopConfigFormat();

        for (Map.Entry<Class<? extends Config>, @Nullable Config> entry : defaults.entrySet()) {
            //noinspection unchecked
            loadConfig(entry.getKey(), entry.getValue(), fmt);
        }

        initialized = true;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Config> Optional<C> get(Class<C> clazz) {
        @Nullable C config = (C) loaded.get(clazz);
        return Optional.ofNullable(config);
    }

    @SuppressWarnings("unchecked")
    private <C extends Config> void loadConfig(Class<? extends Config> clazz, @Nullable Config defaultInstance) {
        ConfigFormat fmt = Objects.requireNonNull(format,
                "loadConfig called without a ConfigFormat — this is a bug");
        loadConfig(clazz, defaultInstance, fmt);
    }

    @SuppressWarnings("unchecked")
    private <C extends Config> void loadConfig(Class<? extends Config> clazz, @Nullable Config defaultInstance, ConfigFormat fmt) {
        @Nullable Config resolved = defaultInstance;
        String key = resolveKey(clazz);

        for (ConfigSource source : sources) {
            Optional<InputStream> data = source.read(key);
            if (data.isEmpty()) {
                continue;
            }
            try (InputStream in = data.get()) {
                @Nullable C deserialized = fmt.deserialize((Class<C>) clazz, in);
                if (deserialized == null) {
                    LOGGER.warn("ConfigFormat returned null for {} from {} — skipping source", clazz.getSimpleName(), source.getClass().getSimpleName());
                    continue;
                }
                resolved = deserialized;
                LOGGER.debug("Loaded {} from {} (priority {})", clazz.getSimpleName(), source.getClass().getSimpleName(), source.priority());
            } catch (IOException e) {
                LOGGER.warn("Failed to load {} from {} — skipping source", clazz.getSimpleName(), source.getClass().getSimpleName(), e);
            }
        }

        loaded.put(clazz, resolved);
        LOGGER.info("Config {} resolved", clazz.getSimpleName());
    }

    @Override
    public ConfigRegistry asRegistry() {
        if (!initialized) {
            throw new IllegalStateException("ConfigLoader not yet initialized — call initialize() before asRegistry()");
        }
        // Snapshot the loaded map at this point in time
        Map<Class<? extends Config>, @Nullable Config> snapshot = Map.copyOf(loaded);
        return new ConfigRegistry() {
            @Override
            @SuppressWarnings("unchecked")
            public <C extends Config> Optional<C> get(Class<C> clazz) {
                return Optional.ofNullable((C) snapshot.get(clazz));
            }
        };
    }

    private static String resolveKey(Class<? extends Config> clazz) {
        @Nullable ConfigFile annotation = clazz.getAnnotation(ConfigFile.class);
        return annotation != null ? annotation.value() : clazz.getSimpleName();
    }
}
