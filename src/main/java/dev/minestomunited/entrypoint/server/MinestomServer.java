package dev.minestomunited.entrypoint.server;

import dev.minestomunited.entrypoint.config.ConfigRegistry;
import dev.minestomunited.entrypoint.minestom.MinestomService;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;

/**
 * Central access point for server-level services.
 *
 * <p>Implementations are provided by individual server modules.
 */
public interface MinestomServer {

    SessionService sessionService();

    PlayerService playerService();

    MinestomService<?> minestomService();

    ConfigRegistry configRegistry();
}
