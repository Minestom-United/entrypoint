package dev.minestomunited.entrypoint.ipc;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * An In-Memory implementation of a {@link RequestReply} message system.
 */
public class MemoryRequestReply implements RequestReply {
    private final Map<String, Function<byte[], CompletableFuture<byte[]>>> handlers = new ConcurrentHashMap<>();

    @Override
    public CompletableFuture<byte[]> request(String channel, byte[] data) {
        Function<byte[], CompletableFuture<byte[]>> handler = handlers.get(channel);

        if (handler == null) {
            return CompletableFuture.failedFuture(new NoHandlerException(channel));
        }

        try {
            return handler.apply(data);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public Subscription registerReply(String channel, Function<byte[], CompletableFuture<byte[]>> handler) {
        if (handlers.putIfAbsent(channel, handler) != null) {
            throw new IllegalStateException("A handler is already registered on channel: " + channel);
        }
        return () -> handlers.remove(channel, handler);
    }

    public static class NoHandlerException extends RuntimeException {
        public NoHandlerException(String channel) {
            super("No reply handler registered on channel: " + channel);
        }
    }
}
