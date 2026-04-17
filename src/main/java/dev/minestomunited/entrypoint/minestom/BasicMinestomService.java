package dev.minestomunited.entrypoint.minestom;

import dev.minestomunited.entrypoint.config.ConfigRegistry;
import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import dev.minestomunited.entrypoint.minestom.player.MinestomPlayerService;
import dev.minestomunited.entrypoint.minestom.player.NetworkPlayer;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;

public class BasicMinestomService<P extends Player & NetworkPlayer> implements MinestomService<P> {

    private MinecraftServer server = null;
    private final ConfigRegistry registry;
    private final MinestomPlayerService.MinestomPlayerProvider<P> playerProvider;
    private final SessionService sessionService;
    private final PlayerService playerService;
    private MinestomPlayerService<P> minestomPlayerService = null;

    /**
     * Creates a new service backed by the given registry and supporting services.
     *
     * @param registry       the config registry used to resolve startup configuration
     * @param sessionService the session service for tracking online players
     * @param playerService  the player service for loading and persisting player data
     * @param playerProvider factory that instantiates the player object per connection
     */
    public BasicMinestomService(
            ConfigRegistry registry,
            SessionService sessionService,
            PlayerService playerService,
            MinestomPlayerService.MinestomPlayerProvider<P> playerProvider) {
        this.registry = registry;
        this.sessionService = sessionService;
        this.playerService = playerService;
        this.playerProvider = playerProvider;
    }

    @Override
    public void setup(Auth auth) {
        if (server != null) {
            throw new IllegalStateException("server already setup");
        }
        server = MinecraftServer.init(auth);
        minestomPlayerService = new MinestomPlayerService<>(eventNode(), sessionService, playerService, playerProvider);
    }

    @Override
    public void run() {
        if (server == null) {
            throw new IllegalStateException("server not setup, did you forget to call setup()?");
        }
        ServerConfig serverConfig = registry.get(ServerConfig.class)
                .orElseThrow(() -> new IllegalStateException("ServerConfig not loaded"));
        server.start(serverConfig.host(), serverConfig.port());
    }

    @Override
    public EventNode<Event> eventNode() {
        if (server == null) {
            throw new IllegalStateException("server not setup, did you forget to call setup()?");
        }
        return MinecraftServer.getGlobalEventHandler();
    }

    @Override
    public MinestomPlayerService<P> playerService() {
        return minestomPlayerService;
    }
}
