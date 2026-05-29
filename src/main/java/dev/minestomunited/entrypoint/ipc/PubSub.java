package dev.minestomunited.entrypoint.ipc;

import java.util.function.Consumer;

/**
 * An abstract Pub-Sub ipc paradigm specification
 */
public interface PubSub {

    /**
     * Publish to this PubSub broker
     *
     * @param channel The channel to publish to
     * @param data    the bytes to push to this broker
     */
    void publish(String channel, byte[] data);

    /**
     *
     * @param channel  the channel to subscribe to
     * @param consumer The consumer called whenever a message is received on this channel
     * @return the {@link Subscription} that can be used to cancel this subscription
     */
    Subscription subscribe(String channel, Consumer<byte[]> consumer);
}
