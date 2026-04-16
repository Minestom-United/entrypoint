package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.soruce.EnvironmentVariableConfigSource;

public class DemoMain {

    static void main(String[] args) {
        EntryPoint.run(args, (configLoader) -> {
            configLoader
                    .addSource(new EnvironmentVariableConfigSource())
            ;
            return new DemoServer(configLoader);
        });
    }
}
