package dev.minestomunited.entrypoint.player;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Immutable snapshot of a player's connection data captured at session creation.
 */
public interface PlayerData {

    String username();

    UUID uuid();

    PlayerSkin playerSkin();

    String ip();

    @Nullable
    String proxy();

    String version();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            String ip,
            @Nullable
            String proxy,
            String version
    ) implements PlayerData {
    }
}
