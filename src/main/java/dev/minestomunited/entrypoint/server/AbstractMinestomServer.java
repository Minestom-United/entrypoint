package dev.minestomunited.entrypoint.server;

import dev.minestomunited.entrypoint.config.ConfigRegistry;

public abstract class AbstractMinestomServer implements MinestomServer {

    private final ConfigRegistry registry;

    protected AbstractMinestomServer(ConfigRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ConfigRegistry configRegistry() {
        return registry;
    }
}
