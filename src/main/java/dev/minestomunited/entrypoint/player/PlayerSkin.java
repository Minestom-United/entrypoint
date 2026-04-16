package dev.minestomunited.entrypoint.player;

/**
 * Holds a player's skin data as returned by the Mojang session server.
 *
 * @param textures  base64-encoded textures property value
 * @param signature base64-encoded signature for the textures (used for online-mode verification)
 */
public record PlayerSkin(String textures, String signature) {
}
