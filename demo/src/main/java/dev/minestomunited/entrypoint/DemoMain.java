package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    static void main(String[] args) {
        EntryPoint
                .builder()
                .register(DemoConfig.class, new DemoConfig("A Minestom Server", 100))
                .onConfigLoaded(registry -> {
                    ServerConfig server = registry.get(ServerConfig.class).orElseThrow();
                    DemoConfig demo = registry.get(DemoConfig.class).orElseThrow();
                    LOGGER.info("Config loaded — binding {}:{} | motd='{}' maxPlayers={}",
                            server.host(), server.port(), demo.motd(), demo.maxPlayers());
                })
                .server(DemoServer::new)
                .afterStartup(server -> {
                    EventNode<Event> eventNode = server.minestomService().eventNode();
                    DemoConfig demo = server.configRegistry().get(DemoConfig.class).orElseThrow();
                    eventNode.addListener(ServerListPingEvent.class, event -> {
                        Status newStatus = Status.builder()
                                .description(Component.text(demo.motd()))
                                .playerInfo(MinecraftServer.getConnectionManager().getOnlinePlayerCount(), demo.maxPlayers())
                                .build();
                        event.setStatus(newStatus);
                    });
                })
                .run(args);
    }
}
