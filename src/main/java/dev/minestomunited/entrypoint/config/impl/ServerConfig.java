package dev.minestomunited.entrypoint.config.impl;

import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFile;

@ConfigFile("server.yml")
public record ServerConfig(
        String host,
        int port
) implements Config {
}
