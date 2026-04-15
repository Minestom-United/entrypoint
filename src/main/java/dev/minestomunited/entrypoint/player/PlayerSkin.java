package dev.minestomunited.entrypoint.player;

import net.minestom.server.network.player.ResolvableProfile;

/**
 * Holds a player's skin data as returned by the Mojang session server.
 *
 * @param textures  base64-encoded textures property value
 * @param signature base64-encoded signature for the textures (used for online-mode verification)
 */
public record PlayerSkin(String textures, String signature) {
}
