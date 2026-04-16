package dev.minestomunited.entrypoint.session;

import dev.minestomunited.entrypoint.player.PlayerSkin;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

/**
 * An online player's session
 */
public interface PlayerSession {
    /**
     * The online player's uuid
     *
     * @return the uuid
     */
    UUID uuid();

    /**
     * The online player's username
     *
     * @return the username
     */
    String username();

    /**
     * The online player's skin
     *
     * @return the skin
     */
    PlayerSkin playerSkin();

    /**
     * The timestamp of when the session was created (when the player joined)
     *
     * @return the timestamp
     */
    Instant createdAt();

    /**
     * The id of the proxy the player is connected to, null if the player didn't via proxy
     *
     * @return the proxy id
     */
    @Nullable
    String proxyId();

    /**
     * The id of the server the player is connected to, null if the player is switching servers
     *
     * @return the server id
     */
    @Nullable
    String serverId();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            Instant createdAt,
            @Nullable String proxyId,
            @Nullable String serverId) implements PlayerSession {
    }
}
