package dev.minestomunited.entrypoint.config;

import java.util.Optional;

/**
 * Read-only view of loaded {@link Config} instances.
 *
 * <p>Obtained via {@link ConfigLoader#asRegistry()} after initialization.
 * Passed to server factories and lifecycle hooks — intentionally prevents
 * mutation of the config state at runtime.
 */
public interface ConfigRegistry {

    /**
     * Retrieve a loaded config instance by type.
     *
     * @param clazz the config class
     * @param <C>   the config type
     * @return the loaded instance, or empty if not registered
     */
    <C extends Config> Optional<C> get(Class<C> clazz);
}
