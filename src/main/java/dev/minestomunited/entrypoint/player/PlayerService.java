package dev.minestomunited.entrypoint.player;

import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Blocking
public interface PlayerService {

    /**
     * Updates the player data in the database.
     *
     * @param playerData the player data to update
     */
    void updatePlayerData(PlayerData playerData);

    /**
     * Unloads cached player data for the given player.
     *
     * @param playerId uuid of the player to unload
     */
    void unloadPlayerData(UUID playerId);

    /**
     * Loads player data from the database.
     *
     * <p>Returns {@code null} if no data exists for the given player.
     *
     * @param playerId uuid of the player
     * @return the player data, or {@code null} if not found
     */
    @Nullable
    PlayerData loadPlayerData(UUID playerId);
}
