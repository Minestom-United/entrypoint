package dev.minestomunited.entrypoint.player;

import java.util.UUID;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;

/**
 * Immutable snapshot of a player's connection data captured at session creation.
 */
public interface PlayerData {

    Tag<PlayerData> TAG = Tag.Transient("player_data");

    /**
     * The online player's uuid.
     *
     * @return the uuid
     */
    UUID uuid();

    /**
     * The online player's username.
     *
     * @return the username
     */
    String username();

    /**
     * The online player's skin.
     *
     * @return the skin
     */
    PlayerSkin playerSkin();

    /**
     * The online player's ip.
     *
     * @return the ip
     */
    String ip();

    /**
     * The online player's proxy, null if not connected via proxy.
     *
     * @return the proxy id or null
     */
    @Nullable
    String proxy();

    /**
     * The online player's version.
     *
     * @return the version
     */
    String version();

    record Generic(
            UUID uuid,
            String username,
            PlayerSkin playerSkin,
            String ip,
            @Nullable
            String proxy,
            String version
    ) implements PlayerData {
    }
}
