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
import org.jetbrains.annotations.Nullable;

public class JsonCodecConfigFormat implements ConfigFormat {
    private final Map<Class<? extends Config>, Codec<? extends Config>> codecs;

    public JsonCodecConfigFormat(Map<Class<? extends Config>, Codec<? extends Config>> codecs) {
        this.codecs = codecs;
    }

    @Override
    public @Nullable <C extends Config> C deserialize(Class<C> type, InputStream in) {
        //noinspection unchecked
        Codec<C> codec = (Codec<C>) codecs.get(type);
        if (codec == null) {
            return null;
        }
        return codec.decode(Transcoder.JSON, JsonParser.parseReader(new InputStreamReader(in)))
                .orElseThrow("Failed to decode json for config " + type.getName());
    }

    @Override
    public <C extends Config> void serialize(C config, OutputStream out) throws IOException {
        //noinspection unchecked
        Codec<C> codec = (Codec<C>) codecs.get(config.getClass());
        if (codec == null) {
            return;
        }
        String json = codec.encode(Transcoder.JSON, config)
                .orElseThrow("Failed to decode json for config " + codec.getClass().getName()).toString();
        out.write(json.getBytes());
    }
}
