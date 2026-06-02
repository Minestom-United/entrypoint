package dev.minestomunited.entrypoint.ipc;

import org.jetbrains.annotations.NonBlocking;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * An abstract Request-Reply IPC paradigm specification
 */
public interface RequestReply {

    /**
     * Request data based on channel and input data
     *
     * @param channel the channel to request over
     * @param data    the data to send to the reply handler.
     * @return the future completed upon the reply of the handler.
     */
    @NonBlocking
    CompletableFuture<byte[]> request(String channel, byte[] data);

    /**
     * Register a reply handler to reply to requests
     *
     * @param channel      the channel to handle requests over
     * @param replyHandler the reply handler to process a request.The function's parameter is the bytes, possibly
     *                     empty, of the request. It should return a byte array as a response to the request.
     * @return the {@link Subscription} that can be used to cancel this handler
     */
    @NonBlocking
    Subscription registerReply(String channel, Function<byte[], CompletableFuture<byte[]>> replyHandler);
}
