package dev.minestomunited.entrypoint.minestom;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import dev.minestomunited.entrypoint.minestom.player.MinestomPlayerService;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;

public class BasicMinestomService implements MinestomService {

    private MinecraftServer server = null;
    private final ConfigLoader configLoader;
    private MinestomPlayerService minestomPlayerService = null;
    private final SessionService sessionService;
    private final PlayerService playerService;

    public BasicMinestomService(ConfigLoader configLoader, SessionService sessionService, PlayerService playerService) {
        this.configLoader = configLoader;
        this.sessionService = sessionService;
        this.playerService = playerService;
    }

    @Override
    public void setup(Auth auth) {
        if (server != null) {
            throw new IllegalStateException("server already setup");
        }
        server = MinecraftServer.init(auth);
        minestomPlayerService = new MinestomPlayerService(eventNode(), sessionService, playerService);
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

    @Override
    public EventNode<Event> eventNode() {
        if(server == null) {
            throw new IllegalStateException("server not setup, did you forget to call setup()?");
        }
        return MinecraftServer.getGlobalEventHandler();
    }

    @Override
    public MinestomPlayerService playerService() {
        return minestomPlayerService;
    }
}
