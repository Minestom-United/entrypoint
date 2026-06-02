package dev.minestomunited.entrypoint;

import dev.minestomunited.common.config.ConfigRegistry;
import dev.minestomunited.entrypoint.minestom.BasicMinestomService;
import dev.minestomunited.entrypoint.minestom.MinestomService;
import dev.minestomunited.entrypoint.minestom.player.BasicNetworkPlayer;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import dev.minestomunited.entrypoint.session.SessionService;
import net.minestom.server.Auth;

public class DemoServer extends AbstractMinestomServer {

    private final DemoConfig demoConfig;
    private final SessionService sessionService;
    private final PlayerService playerService;
    private final MinestomService<BasicNetworkPlayer> minestomService;

    protected DemoServer(ConfigRegistry registry) {
        super(registry);
        demoConfig = registry.get(DemoConfig.class)
                .orElseThrow(() -> new IllegalStateException("DemoConfig not loaded"));
        sessionService = new MemorySessionService();
        playerService = new MemoryPlayerService();
        minestomService = new BasicMinestomService<>(this, registry, sessionService, playerService, BasicNetworkPlayer::new);
    }

    /**
     * Returns the demo configuration loaded at startup.
     *
     * @return the demo config
     */
    public DemoConfig demoConfig() {
        return demoConfig;
    }

    @Override
    public SessionService sessionService() {
        return sessionService;
    }

    @Override
    public PlayerService playerService() {
        return playerService;
    }

    @Override
    public MinestomService<BasicNetworkPlayer> minestomService() {
        return minestomService;
    }

    @Override
    public Auth auth() {
        return new Auth.Online();
    }

    @Override
    public boolean isStandalone() {
        return true;
    }
}
