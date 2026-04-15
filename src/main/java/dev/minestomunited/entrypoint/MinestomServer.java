package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;

public interface MinestomServer {

    SessionService sessionService();

    PlayerService playerService();

}
