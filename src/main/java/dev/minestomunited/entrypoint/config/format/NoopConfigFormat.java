package dev.minestomunited.entrypoint.config.format;

import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFormat;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.annotations.Nullable;

public class NoopConfigFormat implements ConfigFormat {

    @Override
    public @Nullable <C extends Config> C deserialize(Class<C> type, InputStream in) {
        return null;
    }

    @Override
    public void serialize(Config config, OutputStream out) {
    }
}
