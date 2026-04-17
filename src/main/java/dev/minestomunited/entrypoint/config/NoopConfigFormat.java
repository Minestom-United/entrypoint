package dev.minestomunited.entrypoint.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NoopConfigFormat implements ConfigFormat {
    @Override
    public <C extends Config> C deserialize(Class<C> type, InputStream in) throws IOException {
        return null;
    }

    @Override
    public void serialize(Config config, OutputStream out) throws IOException {
    }
}
