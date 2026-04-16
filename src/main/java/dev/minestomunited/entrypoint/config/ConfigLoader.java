package dev.minestomunited.entrypoint.config;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Loads, resolves, and provides access to {@link Config} instances.
 *
 * <p>Implementations are discovered via {@link java.util.ServiceLoader}. A single
 * implementation must be present on the classpath at runtime.
 *
 * <p>Sources are layered by {@link ConfigSource#priority()} — higher priority
 * sources override lower priority ones for the same config key.
 *
 * <p>Typical usage:
 * <pre>{@code
 * configLoader
 *     .withFormat(new YamlConfigFormat())
 *     .addSource(new ClasspathConfigSource())   // priority 0 — bundled defaults
 *     .addSource(new FileConfigSource(dataDir)) // priority 100 — operator overrides
 *     .addSource(new EnvConfigSource())         // priority 200 — env overrides
 *     .initialize(args);                        // parses CLI args, priority 300
 *
 * ServerConfig cfg = configLoader.get(ServerConfig.class);
 * }</pre>
 */
public interface ConfigLoader {

    /**
     * Set the format used to deserialize config data from all sources.
     *
     * @param format the format implementation
     * @return this, for chaining
     */
    @Contract("_ -> this")
    ConfigLoader withFormat(ConfigFormat format);

    /**
     * Register a config source. Sources are sorted by {@link ConfigSource#priority()}.
     *
     * @param source the source to add
     * @return this, for chaining
     */
    @Contract("_ -> this")
    ConfigLoader addSource(ConfigSource source);

    /**
     * Parse CLI arguments and finalize loading of all registered configs.
     * Must be called after all sources and format are configured.
     *
     * @param args CLI arguments from {@code main(String[] args)}
     * @return this, for chaining
     */
    @Contract("_ -> this")
    ConfigLoader initialize(String[] args);

    /**
     * Retrieve a loaded config instance by type.
     *
     * <p>The config class must have been resolved during {@link #initialize(String[])},
     * either via classpath scanning or explicit {@link #register(Class, Config)}.
     *
     * @param clazz the config class
     * @param <C>   the config type
     * @return the loaded instance
     * @throws IllegalStateException if the config was not loaded
     */
    <C extends Config> Optional<C> get(Class<C> clazz);

    /**
     * Explicitly register a config class with a fallback default instance.
     *
     * <p>The default is used when no source provides data for this config.
     * Sources with higher priority can still override individual fields.
     *
     * @param clazz    the config class
     * @param defaults the default instance (all fields set to sane defaults)
     * @param <C>      the config type
     * @return this, for chaining
     */
    <C extends Config> ConfigLoader register(Class<C> clazz, @Nullable C defaults);
}
