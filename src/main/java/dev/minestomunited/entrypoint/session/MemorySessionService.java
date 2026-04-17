package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.environment.SharedConstants;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jspecify.annotations.Nullable;

public class MemorySessionService implements SessionService {

    private final Map<UUID, PlayerSession> sessions = new ConcurrentHashMap<>();

    @Override
    public PlayerSession createSession(
            UUID uuid, String username, @Nullable PlayerSkin playerSkin,
            String ip, @Nullable String proxy, String version) {
        PlayerSession session = new PlayerSession.Generic(
                uuid, username, playerSkin, Instant.now(),
                ip, proxy, SharedConstants.HOSTNAME, version);
        sessions.put(uuid, session);
        return session;
    }

    @Override
    public void deleteSession(UUID uuid) {
        sessions.remove(uuid);
    }

    @Override
    public Collection<PlayerSession> sync() {
        return sessions.values();
    }
}
