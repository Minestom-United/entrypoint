package dev.minestomunited.entrypoint.ipc;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract Pub-Sub ipc paradigm specification
 */
public interface PubSub {

    /**
     * Publish to this PubSub broker
     *
     * @param channel The channel to publish to
     * @param data    the payload bytes to publish
     */
    @Blocking
    void publish(@NotNull String channel, byte[] data);

    /**
     * Subscribe to a channel
     *
     * @param channel  the channel to subscribe to
     * @param consumer The consumer called whenever a message is received on this channel
     * @return the {@link Subscription} that can be used to cancel this subscription
     */
    @NonBlocking
    Subscription subscribe(@NotNull String channel, @NotNull Consumer<byte[]> consumer);

    /**
     * Subscribe to a pattern, or "glob" channel. Depending on the implementation, channels can support
     * wildcards to match multiple channels on a single listener. For this reason, unlike {@link #subscribe},
     * the consumer has 2 arguments, the first argument is the matched channel name, and the second is the
     * received payload
     *
     * @param channel  The channel to subscribe to. Wildcards are supported, but may vary in syntax by implementation.
     * @param consumer The consumer called whenever a message is received on a matching channel
     * @return the {@link Subscription} that can be used to cancel this subscription
     */
    @NonBlocking
    Subscription patternSubscribe(@NotNull String channel, @NotNull BiConsumer<String, byte[]> consumer);
}
