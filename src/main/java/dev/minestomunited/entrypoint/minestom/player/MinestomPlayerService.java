package dev.minestomunited.entrypoint.minestom.player;

import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerService;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import dev.minestomunited.entrypoint.session.SessionService;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MinestomPlayerService<P extends Player & NetworkPlayer> {

    public MinestomPlayerService(EventNode<Event> eventNode, SessionService sessionService,
                                 PlayerService playerService, MinestomPlayerProvider<P> playerProvider) {
        eventNode
                .addListener(PlayerDisconnectEvent.class, event -> {
                    final UUID playerId = event.getPlayer().getUuid();
                    sessionService.deleteSession(playerId);
                    PlayerData playerData = event.getPlayer().getTag(PlayerData.TAG);
                    if (playerData != null) {
                        playerService.updatePlayerData(playerData);
                    }
                    playerService.unloadPlayerData(playerId);
                })
                .addListener(AsyncPlayerPreLoginEvent.class, event ->{
                    final UUID playerId = event.getGameProfile().uuid();
                    CompletableFuture.runAsync(()->{
                        playerService.loadPlayerData(playerId); // TODO(Trop) - consider using CompletableFuture specifically for Services so we can have time outs and background ah stuff
                        // TODO - dont load every time, have a cache / debounce system
                    });
                })
                .addListener(AsyncPlayerConfigurationEvent.class, event -> {
                    final Player player = event.getPlayer();
                    final UUID playerId = player.getUuid();
                    PlayerData playerData = playerService.loadPlayerData(playerId); // Trop: i dont like this, we call it twice, but fuck it for now :)
                    if (playerData == null) {
                        // TODO - implement creating of player data here :)
                        return;
                    }
                    player.setTag(PlayerData.TAG, playerData);
                })
                .addListener(PlayerSpawnEvent.class, event -> {
                    Player player = event.getPlayer();
                    UUID playerId = player.getUuid();
                    // TODO - validate that player skin is set now? it may be set between configure and play state
                    // TODO - get proxy somehow? plugin messages?
                    sessionService.createSession(playerId, player.getUsername(), PlayerSkin.fromMinestom(player.getSkin()), player.getPlayerConnection().getRemoteAddress().toString(), "unknown", MinecraftServer.VERSION_NAME);
                })
        ;
        MinecraftServer.getConnectionManager().setPlayerProvider(playerProvider::createPlayer);
    }


    @FunctionalInterface
    public interface MinestomPlayerProvider<P extends Player & NetworkPlayer> {

        /**
         * Creates a new {@link P} object based on his connection data.
         * <p>
         * Called once a client want to join the server and need to have an assigned player object.
         *
         * @param connection  the player connection
         * @param gameProfile the player game profile
         * @return a newly create {@link P} object
         */
        P createPlayer(PlayerConnection connection, GameProfile gameProfile);
    }
}
