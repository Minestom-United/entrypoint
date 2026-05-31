package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.environment.SharedConstants;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import dev.minestomunited.entrypoint.session.PlayerSession;
import dev.minestomunited.entrypoint.session.SessionService;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.Nullable;

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
