package dev.minestomunited.entrypoint.codec;

import java.time.Duration;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.codec.Codec;
import net.minestom.server.codec.StructCodec;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.PlayerSkin;

@UtilityClass
public final class ExtraCodecs {
    public static final Codec<Pos> POS = StructCodec.struct(
            "x", Codec.DOUBLE, Pos::x,
            "y", Codec.DOUBLE, Pos::y,
            "z", Codec.DOUBLE, Pos::z,
            "yaw", Codec.FLOAT.optional(0f), Pos::yaw,
            "pitch", Codec.FLOAT.optional(0f), Pos::pitch,
            Pos::new
    );

    public static final Codec<Pos> CENTERED_POS = StructCodec.struct(
            "x", Codec.DOUBLE, pos -> pos.x() - 0.5,
            "y", Codec.DOUBLE, Pos::y,
            "z", Codec.DOUBLE, pos -> pos.z() - 0.5,
            "yaw", Codec.FLOAT.optional(0f), Pos::yaw,
            "pitch", Codec.FLOAT.optional(0f), Pos::pitch,
            (x, y, z, yaw, pitch) -> new Pos(x + 0.5, y, z + 0.5, yaw, pitch)
    );

    public static final Codec<BlockVec> BLOCK_VEC = StructCodec.struct(
            "x", Codec.INT, BlockVec::blockX,
            "y", Codec.INT, BlockVec::blockY,
            "z", Codec.INT, BlockVec::blockZ,
            BlockVec::new
    );

    public static final Codec<Component> MINI_MESSAGE = MiniMessage(MiniMessage.miniMessage());

    public static Codec<Component> MiniMessage(MiniMessage miniMessage) {
        return Codec.STRING.transform(miniMessage::deserialize, miniMessage::serialize);
    }

    public static final Codec<PlayerSkin> PLAYER_SKIN = StructCodec.struct(
            "textures", Codec.STRING, PlayerSkin::textures,
            "signature", Codec.STRING, PlayerSkin::signature,
            PlayerSkin::new
    );

    public static final Codec<NamedTextColor> NAMED_TEXT_COLOR =
            Codec.STRING.transform(NamedTextColor.NAMES::value, NamedTextColor::name);

    public static final Codec<Duration> DURATION = DurationCodec.INSTANCE;
}
