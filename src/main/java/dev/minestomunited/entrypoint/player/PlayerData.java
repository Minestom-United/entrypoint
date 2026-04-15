package dev.minestomunited.entrypoint.player;

import java.util.UUID;

/**
 * Immutable snapshot of a player's connection data captured at session creation.
 */
public interface PlayerData {

    String username();

    UUID uuid();

    PlayerSkin playerSkin();

    String ip();

    String proxy();

    String version();

    record Generic(
            String username,
            UUID uuid,
            PlayerSkin playerSkin,
            String ip,
            String proxy,
            String version
    ) implements PlayerData {
    }
}
