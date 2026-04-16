package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import dev.minestomunited.entrypoint.session.SessionService;
import dev.minestomunited.entrypoint.session.SessionServiceImpl;
import org.jspecify.annotations.NonNull;

public class DemoServer extends AbstractMinestomServer {
    private final SessionService sessionService;
    private final PlayerService playerService;

    protected DemoServer(ConfigLoader config) {
        super(config);
        sessionService = new SessionServiceImpl();
        playerService = new PlayerServiceImpl();
    }

    @Override
    public @NonNull SessionService sessionService() {
        return sessionService;
    }

    @Override
    public @NonNull PlayerService playerService() {
        return playerService;
    }
}
