package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.environment.SharedConstants;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.util.Services.Fallback;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;

/**
 * Manages player sessions across the network.
 */
@Blocking
public interface SessionService {
    /**
     * Registers a new player session.
     *
     * @param username   player's username
     * @param uuid       player's UUID
     * @param playerSkin player's skin textures and signature from Mojang
     * @param ip         connecting client IP address
     * @param proxy      the player's connected proxy, null if not connected via proxy
     * @param version    Minecraft protocol version string
     * @return populated {@link PlayerSession} for this session
     */
    PlayerSession createSession(
            UUID uuid,
            String username,
            @Nullable PlayerSkin playerSkin,
            String ip,
            @Nullable String proxy,
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
     * @return collection of active {@link PlayerSession}s
     */
    Collection<PlayerSession> sync();

    @SuppressWarnings("unused")
    class Noop implements SessionService, Fallback {

        @Override
        public PlayerSession createSession(
                UUID uuid,
                String username,
                @Nullable PlayerSkin playerSkin,
                String ip,
                @Nullable String proxy,
                String version) {
            return new PlayerSession.Generic(
                    uuid, username, playerSkin, Instant.now(),
                    ip, proxy, SharedConstants.HOSTNAME, version);
        }

        @Override
        public void deleteSession(UUID uuid) {
        }

        @Override
        public Collection<PlayerSession> sync() {
            return List.of();
        }
    }
}
