package dev.minestomunited.entrypoint.minestom.player;

import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

public class BasicNetworkPlayer extends Player implements NetworkPlayer {

    public BasicNetworkPlayer(PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);
    }
}
