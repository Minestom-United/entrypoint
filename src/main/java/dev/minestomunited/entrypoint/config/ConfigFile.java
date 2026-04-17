package dev.minestomunited.entrypoint.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Associates a {@link Config} class with a key used to look it up via {@link ConfigSource}.
 *
 * <p>The value should be an extension-free name. {@link ConfigSource} and {@link ConfigFormat}
 * implementations are responsible for appending any file extension they require.
 *
 * <p>When absent, the simple class name is used as the key.
 *
 * <pre>{@code
 * @ConfigFile("server")
 * public record ServerConfig(String host, int port) implements Config {}
 * }</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigFile {
    /**
     * The config key (extension-free filename) used to look up this config from sources.
     *
     * @return the config key
     */
    String value();
}
