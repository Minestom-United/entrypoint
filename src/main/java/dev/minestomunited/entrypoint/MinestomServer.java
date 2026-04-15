package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.playerdata.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;

/**
 * Central access point for server-level services.
 *
 * <p>Implementations are provided by individual server modules.
 */
public interface MinestomServer {

    SessionService sessionService();

    PlayerService playerService();

}
