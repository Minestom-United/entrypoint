package dev.minestomunited.entrypoint.player;

import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Blocking
public interface PlayerService {

    /**
     * Updates the player data in the database
     * @param playerData the player data to update
     */
    void updatePlayerData(PlayerData playerData);


    void unloadPlayerData(UUID playerId);

    /**
     * Loads player data from the database
     * Null if no player data is found
     *
     * @param playerId uuid of the player
     * @return the player data or null
     */
    @Nullable
    PlayerData loadPlayerData(UUID playerId);
}
