package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.environment.Environment;
import dev.minestomunited.entrypoint.environment.SharedConstants;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;

public class EntryPoint {

    // list of modules from ServiceLoader

    static void main() {
        Auth auth;
        if(SharedConstants.ENVIRONMENT.test(Environment.TESTING)){
            auth = new Auth.Offline();
        } else if(SharedConstants.PROXIED_SECRET != null) {
            auth = new Auth.Velocity(SharedConstants.PROXIED_SECRET);
        } else {
            auth = new Auth.Online();
        }
        MinecraftServer mc = MinecraftServer.init(auth);

        mc.start("0.0.0.0", 25565);
    }
}
