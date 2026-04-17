package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFile;

/**
 * Demo-specific configuration loaded at startup.
 *
 * <p>Override defaults by providing a {@code demo} config file or the
 * {@code MINESTOM_DEMO} environment variable when a real {@link dev.minestomunited.entrypoint.config.ConfigFormat} is registered.
 *
 * @param motd       message of the day shown in server list pings
 * @param maxPlayers maximum number of concurrent players allowed
 */
@ConfigFile("demo")
public record DemoConfig(
        String motd,
        int maxPlayers
) implements Config {
}
