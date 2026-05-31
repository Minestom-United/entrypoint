package dev.minestomunited.entrypoint.ipc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

/**
 * An In-Memory implementation of a {@link PubSub} message broker.
 */
public class MemoryPubSub implements PubSub {

    private final Map<String, List<Consumer<byte[]>>> subscribers = new ConcurrentHashMap<>();
    private final Map<String, List<BiConsumer<String, byte[]>>> patternSubscribers = new ConcurrentHashMap<>();

    @Override
    public void publish(@NotNull String channel, byte[] data) {
        subscribers.getOrDefault(channel, List.of()).forEach(consumer -> consumer.accept(data));

        patternSubscribers.forEach((pattern, consumers) -> {
            if (matchesPattern(pattern, channel)) {
                consumers.forEach(consumer -> consumer.accept(channel, data));
            }
        });
    }

    @Override
    @NonBlocking
    public Subscription subscribe(@NotNull String channel, @NotNull Consumer<byte[]> consumer) {
        subscribers.computeIfAbsent(channel, _ -> new CopyOnWriteArrayList<>()).add(consumer);
        return () -> subscribers.getOrDefault(channel, new ArrayList<>()).remove(consumer);
    }

    /**
     *
     * @param channel  The channel to subscribe to. Standard glob wildcards are supported:
     *                 <p>
     *                 {@code *} matches any character(s) within the token: {@code foo.*} matches {@code foo.bar}
     *                 but not {@code foo.bar.baz}.
     *                 <p>
     *                 {@code **} matches across tokens: {@code foo.**} matches {@code foo.bar}
     *                 and {@code foo.bar.baz}.
     *                 <p>
     *                 {@code ?} matches a single character: {@code foo.b?r} matches {@code foo.bar}
     *                 {@code foo.ber}, and {@code foo.bir}, but not {@code foo.b.r}.
     *                 <p>
     * @param consumer The consumer called whenever a message is received on a matching channel
     * @return the {@link Subscription} that can be used to cancel this subscription
     */
    @Override
    @NonBlocking
    public Subscription patternSubscribe(@NotNull String channel, @NotNull BiConsumer<String, byte[]> consumer) {
        patternSubscribers.computeIfAbsent(channel, _ -> new CopyOnWriteArrayList<>()).add(consumer);
        return () -> patternSubscribers.getOrDefault(channel, new ArrayList<>()).remove(consumer);
    }

    private boolean matchesPattern(@NotNull String pattern, @NotNull String channel) {
        return channel.matches(globToRegex(pattern));
    }

    private String globToRegex(@NotNull String glob) {
        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < glob.length(); i++) {
            char c = glob.charAt(i);
            switch (c) {
                case '*' -> {
                    if (i + 1 < glob.length() && glob.charAt(i + 1) == '*') {
                        sb.append(".*");
                        i++; // skip next *
                    } else {
                        sb.append("[^.]*");
                    }
                }
                case '?' -> sb.append("[^.]");
                case '.', '(', ')', '+', '|', '^', '$', '@', '%', '{', '}', '[', ']', '\\' -> sb.append('\\').append(c);
                default -> sb.append(c);
            }
        }
        sb.append("$");
        return sb.toString();
    }
}
