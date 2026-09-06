package dev.minestomunited.entrypoint.config;

import dev.minestomunited.common.config.Config;
import dev.minestomunited.common.config.ConfigFile;
import net.minestom.server.codec.Codec;
import net.minestom.server.codec.StructCodec;

@ConfigFile("server")
public record ServerConfig(
        String host,
        int port
) implements Config {

    public static final Codec<ServerConfig> CODEC = StructCodec.struct(
            "host", Codec.STRING, ServerConfig::host,
            "port", Codec.INT, ServerConfig::port,
            ServerConfig::new
    );

}
