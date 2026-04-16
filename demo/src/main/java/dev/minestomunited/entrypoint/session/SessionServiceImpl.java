package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionServiceImpl implements SessionService {
    private final Map<UUID, PlayerSession> sessions = new ConcurrentHashMap<>();

    @Override
    public @NonNull PlayerData createSession(@NonNull UUID uuid, @NonNull String username, @NonNull PlayerSkin playerSkin, @NonNull String ip, @Nullable String proxy, @NonNull String version) {
        sessions.put(uuid, new PlayerSession.Generic(uuid, username, playerSkin, Instant.now(), proxy, ip, version));
        return new PlayerData.Generic(uuid, username, playerSkin, ip, proxy, version);
    }

    @Override
    public void deleteSession(@NonNull UUID uuid) {
        sessions.remove(uuid);
    }

    @Override
    public @NonNull Collection<PlayerSession> sync() {
        return sessions.values();
    }
}
