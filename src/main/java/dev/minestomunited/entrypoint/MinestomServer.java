package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.playerdata.PlayerService;
import dev.minestomunited.entrypoint.session.SessionService;

public interface MinestomServer {

    SessionService sessionService();

    PlayerService playerService();

}
