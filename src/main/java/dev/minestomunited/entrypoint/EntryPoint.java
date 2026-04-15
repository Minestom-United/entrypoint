package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.ConfigLoader;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;

import java.util.function.Function;

public class EntryPoint {

    public static void run(Function<ConfigLoader, ? extends AbstractMinestomServer> serverFactory) {
        long start = System.currentTimeMillis();
    }
}
