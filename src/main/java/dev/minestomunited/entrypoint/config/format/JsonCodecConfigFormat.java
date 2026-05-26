package dev.minestomunited.entrypoint.config.format;

import com.google.gson.JsonParser;
import dev.minestomunited.entrypoint.config.Config;
import dev.minestomunited.entrypoint.config.ConfigFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import net.minestom.server.codec.Codec;
import net.minestom.server.codec.Transcoder;
import org.jspecify.annotations.Nullable;

public class JsonCodecConfigFormat implements ConfigFormat {
    private final Map<Class<?>, Codec<?>> codecs;

    public JsonCodecConfigFormat(Map<Class<?>, Codec<?>> codecs) {
        this.codecs = codecs;
    }

    @Override
    public @Nullable <C extends Config> C deserialize(Class<C> type, InputStream in) {
        Codec<?> codec = codecs.get(type);
        if (codec == null) {
            return null;
        }
        //noinspection unchecked
        return (C) codec.decode(Transcoder.JSON, JsonParser.parseReader(new InputStreamReader(in)))
                .orElseThrow("Failed to decode json");
    }

    @Override
    public void serialize(Config config, OutputStream out) throws IOException {

    }
}
