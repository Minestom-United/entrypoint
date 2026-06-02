package dev.minestomunited.entrypoint.session;

import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

/**
 * An online player's session.
 */
public interface PlayerSession {
    /**
     * The session player's uuid.
     *
     * @return the uuid
     */
    UUID uuid();

    /**
     * The session player's username.
     *
     * @return the username
     */
    String username();

    /**
     * The session player's skin.
     *
     * @return the skin
     */
    @Nullable
    PlayerSkin playerSkin();

    /**
     * The timestamp of when the session was created (when the player joined).
     *
     * @return the timestamp
     */
    Instant createdAt();

    /**
     * The ip of the player's client.
     *
     * @return the ipv4 address (potential for ipv6 in the future if we support it)
     */
    String clientIp();

    /**
     * The id of the proxy the player is connected to, null if the player didn't connect via proxy.
     *
     * @return the proxy id
     */
    @Nullable
    String proxy();

    /**
     * The id of the server the player is connected to, null if the player hasn't connected to a server yet.
     *
     * @return the server id
     */
    @Nullable
    String serverId();

    /**
     * The version string of the player's client.
     *
     * @return the version string
     */
    String version();

    record Generic(
            UUID uuid,
            String username,
            @Nullable PlayerSkin playerSkin,
            Instant createdAt,
            String clientIp,
            @Nullable String proxy,
            @Nullable String serverId,
            String version) implements PlayerSession {
    }
}
