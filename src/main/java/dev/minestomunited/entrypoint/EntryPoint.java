package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.config.NoopConfigFormat;
import dev.minestomunited.entrypoint.config.impl.AuthConfig;
import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import net.minestom.server.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.function.Function;

public class EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    public static void run(String[] args, Function<ConfigLoader, ? extends AbstractMinestomServer> serverFactory) {
        long start = System.currentTimeMillis();

        ConfigLoader configLoader;
        {
            ServiceLoader<ConfigLoader> serviceLoader = ServiceLoader.load(ConfigLoader.class);
            configLoader = serviceLoader.findFirst()
                    .orElseThrow(() -> new RuntimeException("No ConfigLoader implementation found"));
        }

        configLoader
                .withFormat(new NoopConfigFormat())
                .register(AuthConfig.class, new AuthConfig(new Auth.Offline()))
                .register(ServerConfig.class, new ServerConfig("0.0.0.0", 25565))
                .initialize(args);

        AbstractMinestomServer server = serverFactory.apply(configLoader);

        AuthConfig authConfig = configLoader.get(AuthConfig.class)
                .orElseThrow(() -> new RuntimeException("AuthConfig not loaded"));

        server.minestomService().setup(authConfig.auth());

        server.minestomService().run();

        long finish = System.currentTimeMillis();
        LOGGER.info("Startup took {}ms", finish - start);
    }
}
