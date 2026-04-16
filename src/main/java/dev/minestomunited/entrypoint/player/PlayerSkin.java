package dev.minestomunited.entrypoint.player;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Holds a player's skin data as returned by the Mojang session server.
 *
 * @param textures  base64-encoded textures property value
 * @param signature base64-encoded signature for the textures (used for online-mode verification)
 */
public record PlayerSkin(String textures, String signature) {

    @Contract(pure = true, value = "null -> null")
    public static @Nullable PlayerSkin fromMinestom(@Nullable net.minestom.server.entity.PlayerSkin minestomSkin) {
        if (minestomSkin == null) {
            return null;
        }
        return new PlayerSkin(minestomSkin.textures(), minestomSkin.signature());
    }

}
