package dev.minestomunited.entrypoint.server.local;

import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import dev.minestomunited.entrypoint.session.PlayerSession;
import dev.minestomunited.entrypoint.session.SessionService;

import java.util.List;
import java.util.UUID;

public class LocalSessionService implements SessionService {

    // TODO - white to memory

    @Override
    public PlayerData createSession(String username, UUID uuid, PlayerSkin playerSkin, String ip, String proxy, String version) {
        return new PlayerData.Generic(username, uuid, playerSkin, ip, proxy, version);
    }

    @Override
    public void deleteSession(UUID uuid) {

    }

    @Override
    public List<PlayerSession> sync() {
        return List.of();
    }
}
