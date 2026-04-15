package dev.minestomunited.entrypoint.player;

import java.time.Instant;
import java.util.UUID;

public interface PlayerSession {
    UUID uuid();

    String username();

    PlayerSkin playerSkin();

    Instant createdAt();

    String proxyId();

    String serverId();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            Instant createdAt,
            String proxyId,
            String serverId) implements PlayerSession {
    }
}
