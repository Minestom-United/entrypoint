package dev.minestomunited.entrypoint.config;

import dev.minestomunited.common.config.Config;
import dev.minestomunited.common.config.ConfigFile;

@ConfigFile("server")
public record ServerConfig(
        String host,
        int port
) implements Config {
}
