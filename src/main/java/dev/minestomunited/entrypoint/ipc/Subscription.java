package dev.minestomunited.entrypoint.ipc;

/**
 * An object representing a cancellable subscription to an IPC line
 */
public interface Subscription extends AutoCloseable {
    /**
     * Closes this {@link Subscription}. Any handlers associated with this {@link Subscription} will no longer be called.
     */
    void unsubscribe();

    @Override
    default void close() {
        unsubscribe();
    }
}
