package dev.minestomunited.entrypoint.session;

import com.google.auto.service.AutoService;
import dev.minestomunited.entrypoint.player.PlayerData;
import dev.minestomunited.entrypoint.player.PlayerSkin;
import net.kyori.adventure.util.Services;
import net.kyori.adventure.util.Services.Fallback;
import org.jetbrains.annotations.Blocking;

import java.util.List;
import java.util.UUID;

@Blocking
public interface SessionService {
    SessionService INSTANCE = Services.serviceWithFallback(SessionService.class)
            .orElseThrow(() -> new IllegalStateException("Couldn't find Noop impl!"));

    PlayerData createSession(
            String username,
            UUID uuid,
            PlayerSkin playerSkin,
            String ip,
            String proxy,
            String version
    );

    void deleteSession(UUID uuid);

    List<PlayerSession> sync();

    @AutoService(SessionService.class)
    class Noop implements SessionService, Fallback {

        @Override
        public PlayerData createSession(String username,
                                        UUID uuid,
                                        PlayerSkin playerSkin,
                                        String ip,
                                        String proxy,
                                        String version) {
            return new PlayerData.Generic(username, uuid, playerSkin, ip, proxy, version);
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
