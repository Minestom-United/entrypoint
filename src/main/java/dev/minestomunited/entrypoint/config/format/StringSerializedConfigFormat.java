package dev.minestomunited.entrypoint.config.format;

import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFormat;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StringSerializedConfigFormat implements ConfigFormat {
    @Override
    public @Nullable <C extends Config> C deserialize(Class<C> type, InputStream in) throws IOException {

    }

    @Override
    public void serialize(Config config, OutputStream out) throws IOException {

    }
}
