package dev.minestomunited.entrypoint.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Associates a {@link Config} class with a file path.
 *
 * <p>The path is relative to the working directory unless the {@link ConfigSource}
 * implementation treats it differently (e.g. classpath resources).
 *
 * <pre>{@code
 * @ConfigFile("server.yml")
 * public record ServerConfig(String host, int port) implements Config {}
 * }</pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigFile {
    String value();
}
