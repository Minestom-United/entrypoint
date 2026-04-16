package dev.minestomunited.entrypoint.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Handles serialization and deserialization of {@link Config} objects.
 *
 * <p>Implementations provide format-specific logic (YAML, JSON, TOML, etc.)
 * and are supplied by the downstream server module — not this library.
 *
 * <pre>{@code
 * // Example implementation using Jackson:
 * public class JsonConfigFormat implements ConfigFormat {
 *     private final ObjectMapper mapper = new ObjectMapper();
 *
 *     public <C extends Config> C deserialize(Class<C> type, InputStream in) throws IOException {
 *         return mapper.readValue(in, type);
 *     }
 *
 *     public void serialize(Config config, OutputStream out) throws IOException {
 *         mapper.writeValue(out, config);
 *     }
 * }
 * }</pre>
 */
public interface ConfigFormat {

    /**
     * Deserialize config data from an input stream into an instance of {@code type}.
     *
     * @param type the config class to deserialize into
     * @param in   the raw data stream
     * @param <C>  the config type
     * @return populated config instance
     * @throws IOException on read or parse failure
     */
    <C extends Config> C deserialize(Class<C> type, InputStream in) throws IOException;

    /**
     * Serialize a config instance to an output stream.
     *
     * @param config the config instance
     * @param out    the target stream
     * @throws IOException on write failure
     */
    void serialize(Config config, OutputStream out) throws IOException;
}
