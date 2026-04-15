package dev.minestomunited.entrypoint.player;

import java.util.UUID;

public interface PlayerData {

    String username();

    UUID uuid();

    PlayerSkin playerSkin();

    String ip();

    String proxy();

    String version();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            String ip,
            String proxy,
            String version
    ) implements PlayerData {
    }
}
