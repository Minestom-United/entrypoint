package dev.minestomunited.entrypoint.config;

/**
 * Marker interface for all config types.
 *
 * <p>Implementations should be plain data classes (POJOs or records) with fields
 * representing config values. Serialization is handled by {@link ConfigFormat},
 * not by the config class itself.
 *
 * <p>Annotate with {@link ConfigFile} to associate this config with a file path.
 */
public interface Config {
}
