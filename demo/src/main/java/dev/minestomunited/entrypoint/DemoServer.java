package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import dev.minestomunited.entrypoint.session.SessionService;
import dev.minestomunited.entrypoint.session.SessionServiceImpl;

public class DemoServer extends AbstractMinestomServer {
    private final SessionService sessionService;
    private final PlayerService playerService;

    protected DemoServer(ConfigLoader config) {
        super(config);
        sessionService = new SessionServiceImpl();
        playerService = new PlayerServiceImpl();
    }

    @Override
    public SessionService sessionService() {
        return sessionService;
    }

    @Override
    public PlayerService playerService() {
        return playerService;
    }
}
