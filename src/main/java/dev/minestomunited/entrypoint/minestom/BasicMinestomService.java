package dev.minestomunited.entrypoint.minestom;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;

public class BasicMinestomService implements MinestomService {

    private MinecraftServer server = null;
    private final ConfigLoader configLoader;

    public BasicMinestomService(ConfigLoader config) {
        this.configLoader = config;
    }

    @Override
    public void setup(Auth auth) {
        if (server != null) {
            throw new IllegalStateException("server already setup");
        }
        server = MinecraftServer.init(auth);
    }

    @Override
    public void run() {
        if (server == null) {
            throw new IllegalStateException("server not setup, did you forget to call setup()?");
        }
        ServerConfig serverConfig = configLoader.get(ServerConfig.class)
                .orElseThrow(() -> new IllegalStateException("ServerConfig not loaded"));
        server.start(serverConfig.host(), serverConfig.port());
    }
}
