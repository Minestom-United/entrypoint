package dev.minestomunited.entrypoint.player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPlayerService implements PlayerService {

    // Not recommended for production, as this is acting as persistence and cache
    private final Map<UUID, PlayerData> playerData = new ConcurrentHashMap<>();

    @Override
    public void updatePlayerData(PlayerData playerData) {
        this.playerData.put(playerData.uuid(), playerData);
    }

    @Override
    public void unloadPlayerData(UUID playerId) {
        // no caching, so we have nothing to unload, playerData acts as cache and persistence layer
    }

    @Override
    public PlayerData loadPlayerData(UUID playerId) {
        return playerData.get(playerId);
    }
}
