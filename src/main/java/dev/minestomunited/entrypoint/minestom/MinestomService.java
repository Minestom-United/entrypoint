package dev.minestomunited.entrypoint.minestom;

import net.minestom.server.Auth;
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

}
