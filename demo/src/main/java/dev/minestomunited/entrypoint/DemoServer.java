package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.ConfigRegistry;
import dev.minestomunited.entrypoint.minestom.BasicMinestomService;
import dev.minestomunited.entrypoint.minestom.MinestomService;
import dev.minestomunited.entrypoint.minestom.player.BasicNetworkPlayer;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import dev.minestomunited.entrypoint.session.MemorySessionService;
import dev.minestomunited.entrypoint.session.SessionService;
import org.jspecify.annotations.NonNull;

public class DemoServer extends AbstractMinestomServer {
    private final SessionService sessionService;
    private final PlayerService playerService;
    private final MinestomService<BasicNetworkPlayer> minestomService;

    protected DemoServer(ConfigRegistry registry) {
        super(registry);
        sessionService = new MemorySessionService();
        playerService = new PlayerServiceImpl();
        minestomService = new BasicMinestomService<>(registry, sessionService, playerService, BasicNetworkPlayer::new);
    }

    @Override
    public @NonNull SessionService sessionService() {
        return sessionService;
    }

    @Override
    public @NonNull PlayerService playerService() {
        return playerService;
    }

    @Override
    public @NonNull MinestomService<BasicNetworkPlayer> minestomService() {
        return minestomService;
    }
}
