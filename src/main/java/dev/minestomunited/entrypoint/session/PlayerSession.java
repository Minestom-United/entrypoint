package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.player.PlayerSkin;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public interface PlayerSession {
    UUID uuid();

    String username();

    PlayerSkin playerSkin();

    Instant createdAt();

    @Nullable
    String proxyId();

    @Nullable
    String serverId();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            Instant createdAt,
            @Nullable String proxyId,
            @Nullable String serverId) implements PlayerSession {
    }
}
