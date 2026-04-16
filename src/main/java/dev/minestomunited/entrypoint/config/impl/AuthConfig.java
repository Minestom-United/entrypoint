package dev.minestomunited.entrypoint.config.impl;

import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFile;
import net.minestom.server.Auth;

@ConfigFile("auth")
public record AuthConfig(
        Auth auth
) implements Config {
}
