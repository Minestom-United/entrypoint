package dev.minestomunited.entrypoint.server;

import dev.minestomunited.common.config.Config;
import dev.minestomunited.common.config.ConfigRegistry;

import java.util.Optional;

public abstract class AbstractMinestomServer implements MinestomServer {

    private final ConfigRegistry registry;

    protected AbstractMinestomServer(ConfigRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ConfigRegistry configRegistry() {
        return registry;
    }

    public <C extends Config> Optional<C> getConfig(Class<C> clazz) {
        return registry.get(clazz);
    }

    public <C extends Config> C getConfigOrThrow(Class<C> clazz) {
        return registry.get(clazz).orElseThrow();
    }
}
