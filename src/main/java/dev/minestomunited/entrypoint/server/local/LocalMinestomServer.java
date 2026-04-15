package dev.minestomunited.entrypoint.server.local;

import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.server.MinestomServer;
import dev.minestomunited.entrypoint.session.SessionService;

public class LocalMinestomServer implements MinestomServer {

    private final SessionService sessionService;
    private final PlayerService playerService;

    public LocalMinestomServer() {
        this.sessionService = new LocalSessionService();
        this.playerService = new LocalPlayerService();
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
