package dev.minestomunited.entrypoint.ipc;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * An In-Memory implementation of a {@link PubSub} message broker.
 */
public class MemoryPubSub implements PubSub {

    private final Map<String, List<Consumer<byte[]>>> subscribers = new ConcurrentHashMap<>();

    @Override
    public void publish(@NotNull String channel, byte[] data) {
        subscribers.getOrDefault(channel, List.of()).forEach(consumer -> consumer.accept(data));
    }

    @Override
    public Subscription subscribe(@NotNull String channel, @NotNull Consumer<byte[]> consumer) {
        subscribers.computeIfAbsent(channel, _ -> new CopyOnWriteArrayList<>()).add(consumer);
        return () -> subscribers.getOrDefault(channel, new ArrayList<>()).remove(consumer);
    }
}
