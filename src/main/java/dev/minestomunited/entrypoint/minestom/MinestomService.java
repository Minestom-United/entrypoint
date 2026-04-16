package dev.minestomunited.entrypoint.minestom;

import dev.minestomunited.entrypoint.minestom.player.MinestomPlayerService;
import net.minestom.server.Auth;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import org.jetbrains.annotations.Blocking;

@Blocking
public interface MinestomService {

    /**
     * Constructs the MinestomServer, and passes Auth as required, also loads registries
     * @param auth the authentication for the server
     */
    void setup(Auth auth);

    /**
     * Starts the MinestomServer
     */
    void run();


    EventNode<Event> eventNode();


    MinestomPlayerService playerService();

}
