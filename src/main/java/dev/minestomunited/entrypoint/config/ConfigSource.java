package dev.minestomunited.entrypoint.config;

import java.io.InputStream;
import java.util.Optional;

/**
 * Abstracts where config data comes from.
 *
 * <p>Multiple sources can be layered in {@link ConfigLoader} with explicit
 * priorities. Higher priority sources override lower priority ones.
 *
 * <p>Built-in sources to implement downstream:
 * <ul>
 *   <li><b>FileConfigSource</b> — reads from a directory on disk</li>
 *   <li><b>ClasspathConfigSource</b> — reads from classpath resources (for bundled defaults)</li>
 *   <li><b>EnvConfigSource</b> — maps environment variables to config fields</li>
 * </ul>
 *
 * <p>Priority order (lowest to highest, last wins):
 * <pre>
 *   classpath defaults → file → env vars → CLI args
 * </pre>
 */
public interface ConfigSource {

    /**
     * Read raw config data for the given key.
     *
     * <p>The key is extension-free (e.g. {@code "server"}, not {@code "server.yml"}).
     * Implementations are responsible for appending whatever extension they require
     * before resolving the underlying resource.
     *
     * @param key the config identifier, usually from {@link ConfigFile#value()} or the simple class name
     * @return the data stream, or empty if this source does not have it
     */
    Optional<InputStream> read(String key);

    /**
     * Priority of this source relative to others registered on the same loader.
     * Higher value = higher priority (overrides lower).
     *
     * <p>Suggested values:
     * <ul>
     *   <li>0 — classpath defaults</li>
     *   <li>100 — file system</li>
     *   <li>200 — environment variables</li>
     *   <li>300 — CLI arguments</li>
     * </ul>
     */
    int priority();
}
