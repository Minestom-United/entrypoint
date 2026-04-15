package dev.minestomunited.entrypoint.session;

import com.google.auto.service.AutoService;
import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import net.kyori.adventure.util.Services.Fallback;

import java.util.List;
import java.util.UUID;

/**
 *
 */
public interface SessionService {
    /**
     * Registers a new player session.
     *
     * @param username   player's username
     * @param uuid       player's UUID
     * @param playerSkin player's skin textures and signature from Mojang
     * @param ip         connecting client IP address
     * @param proxy      proxy node the player connected through
     * @param version    Minecraft protocol version string
     * @return populated {@link PlayerData} for this session
     */
    PlayerData createSession(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            String ip,
            String proxy,
            String version
    );

    /**
     * Removes the session for the given player UUID.
     *
     * @param uuid player UUID to remove
     */
    void deleteSession(UUID uuid);

    /**
     * Returns all currently active sessions across the network.
     *
     * @return list of active {@link PlayerSession}s
     */
    List<PlayerSession> sync();

    @AutoService(SessionService.class)
    class Noop implements SessionService, Fallback {

        @Override
        public PlayerData createSession(
                UUID uuid,
                String username,
                PlayerSkin playerSkin,
                String ip,
                String proxy,
                String version) {
            return new PlayerData.Generic(uuid, username, playerSkin, ip, proxy, version);
        }

        @Override
        public void deleteSession(UUID uuid) {
        }

        @Override
        public List<PlayerSession> sync() {
            return List.of();
        }
    }
}
