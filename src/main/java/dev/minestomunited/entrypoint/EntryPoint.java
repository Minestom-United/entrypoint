package dev.minestomunited.entrypoint;

import dev.minestomunited.entrypoint.config.*;
import dev.minestomunited.entrypoint.config.impl.AuthConfig;
import dev.minestomunited.entrypoint.config.impl.ServerConfig;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minestom.server.Auth;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    private EntryPoint() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final List<ConfigFormat> formats = new ArrayList<>();
        private final Map<Class<?>, Object> defaults = new LinkedHashMap<>();
        private @Nullable ConfigLoader configLoader;
        private @Nullable Function<ConfigRegistry, ? extends AbstractMinestomServer> serverFactory;

        private Consumer<ConfigRegistry> onConfigLoaded = _ -> {
        };
        private Consumer<AbstractMinestomServer> beforeSetup = _ -> {
        };
        private Consumer<AbstractMinestomServer> afterSetup = _ -> {
        };
        private Consumer<AbstractMinestomServer> afterStartup = _ -> {
        };

        private Builder() {
            defaults.put(AuthConfig.class, new AuthConfig(new Auth.Offline()));
            defaults.put(ServerConfig.class, new ServerConfig("0.0.0.0", 25565));
        }

        /**
         * Override the config loader. Defaults to {@link BasicConfigLoader} if not set.
         */
        public Builder configLoader(ConfigLoader loader) {
            this.configLoader = loader;
            return this;
        }

        /**
         * Add a config format. Formats are applied in registration order;
         * later formats take priority over earlier ones on key conflicts.
         */
        public Builder configFormat(ConfigFormat format) {
            this.formats.add(format);
            return this;
        }

        /**
         * Register a config type with a default value.
         * Overrides the framework default for that type if one exists.
         */
        public <T> Builder register(Class<T> type, T defaultValue) {
            defaults.put(type, defaultValue);
            return this;
        }

        /**
         * Provide the server implementation. The {@link ConfigRegistry} is
         * read-only and fully populated before this function is called.
         */
        public Builder server(Function<ConfigRegistry, ? extends AbstractMinestomServer> factory) {
            this.serverFactory = factory;
            return this;
        }

        /**
         * Called after configs are loaded, before the server is instantiated.
         */
        public Builder onConfigLoaded(Consumer<ConfigRegistry> hook) {
            this.onConfigLoaded = hook;
            return this;
        }

        /**
         * Called after the server is instantiated, before {@code setup()} runs.
         */
        public Builder beforeSetup(Consumer<AbstractMinestomServer> hook) {
            this.beforeSetup = hook;
            return this;
        }

        /**
         * Called after {@code setup()} completes, before {@code run()} is called.
         */
        public Builder afterSetup(Consumer<AbstractMinestomServer> hook) {
            this.afterSetup = hook;
            return this;
        }

        /**
         * Called after {@code run()} returns and the server is fully started.
         */
        public Builder afterStartup(Consumer<AbstractMinestomServer> hook) {
            this.afterStartup = hook;
            return this;
        }

        /**
         * Loads configuration, instantiates the server, and starts it.
         *
         * @param args CLI arguments passed to {@code main}
         */
        public void run(String[] args) {
            if (serverFactory == null) {
                throw new IllegalStateException("No server factory provided — call .server() on the builder");
            }

            final long start = System.currentTimeMillis();

            ConfigLoader loader = (configLoader != null) ? configLoader : new BasicConfigLoader();
            formats.forEach(loader::withFormat);
            registerDefaults(loader);
            loader.initialize(args);

            ConfigRegistry registry = loader.asRegistry();
            onConfigLoaded.accept(registry);

            AbstractMinestomServer server = serverFactory.apply(registry);
            beforeSetup.accept(server);

            AuthConfig authConfig = registry.get(AuthConfig.class)
                    .orElseThrow(() -> new IllegalStateException("AuthConfig not present in registry"));
            server.minestomService().setup(authConfig.auth());
            afterSetup.accept(server);

            server.minestomService().run();
            afterStartup.accept(server);

            LOGGER.info("Startup took {}ms", System.currentTimeMillis() - start);
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private void registerDefaults(ConfigLoader loader) {
            defaults.forEach((type, value) -> loader.register((Class) type, (Config) value));
        }
    }
}
