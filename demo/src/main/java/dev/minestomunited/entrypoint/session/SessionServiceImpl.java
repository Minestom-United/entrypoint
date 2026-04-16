package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerSkin;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionServiceImpl implements SessionService {
    private final Map<UUID, PlayerSession> sessions = new ConcurrentHashMap<>();

    @Override
    public PlayerData createSession(UUID uuid, String username, PlayerSkin playerSkin, String ip, String proxy, String version) {
        sessions.put(uuid, new PlayerSession.Generic(uuid, username, playerSkin, Instant.now(), proxy, ip, version));
        return new PlayerData.Generic(uuid, username, playerSkin, ip, proxy, version);
    }

    @Override
    public void deleteSession(UUID uuid) {
        sessions.remove(uuid);
    }

    @Override
    public List<PlayerSession> sync() {
        return sessions.values().stream().toList();
    }
}
