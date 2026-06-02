package dev.minestomunited.entrypoint;

import dev.minestomunited.common.config.*;
import dev.minestomunited.entrypoint.config.ServerConfig;
import dev.minestomunited.entrypoint.server.AbstractMinestomServer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    private EntryPoint() {
    }

    public static <S extends AbstractMinestomServer> Builder<S> builder() {
        return new Builder<>();
    }

    public static final class Builder<S extends AbstractMinestomServer> {

        private final List<ConfigFormat> formats = new ArrayList<>();
        private final List<ConfigSource> sources = new ArrayList<>();
        private final Map<Class<?>, @Nullable Object> defaults = new LinkedHashMap<>();
        private @Nullable ConfigLoader configLoader;
        private @Nullable Function<ConfigRegistry, ? extends S> serverFactory;

        private Consumer<ConfigRegistry> onConfigLoaded = _ -> {
        };
        private Consumer<S> beforeSetup = _ -> {
        };
        private Consumer<S> afterSetup = _ -> {
        };
        private Consumer<S> afterStartup = _ -> {
        };

        private Builder() {
            defaults.put(ServerConfig.class, new ServerConfig("0.0.0.0", 25565));
        }

        /**
         * Override the config loader. Defaults to {@link BasicConfigLoader} if not set.
         */
        public Builder<S> addConfigLoader(ConfigLoader loader) {
            this.configLoader = loader;
            return this;
        }

        /**
         * Add a config format. Formats are applied in registration order;
         * later formats take priority over earlier ones on key conflicts.
         */
        public Builder<S> addConfigFormat(ConfigFormat format) {
            this.formats.add(format);
            return this;
        }

        public Builder<S> addConfigSource(ConfigSource source) {
            this.sources.add(source);
            return this;
        }

        /**
         * Register a config type
         * Overrides the framework default for that type if one exists.
         */
        public <T> Builder<S> registerConfig(Class<T> type) {
            defaults.put(type, null);
            return this;
        }

        /**
         * Register a config type with a default value.
         * Overrides the framework default for that type if one exists.
         */
        public <T> Builder<S> registerConfig(Class<T> type, @Nullable T defaultValue) {
            defaults.put(type, defaultValue);
            return this;
        }

        /**
         * Provide the server implementation. The {@link ConfigRegistry} is
         * read-only and fully populated before this function is called.
         */
        public Builder<S> server(Function<ConfigRegistry, ? extends S> factory) {
            this.serverFactory = factory;
            return this;
        }

        /**
         * Called after configs are loaded, before the server is instantiated.
         */
        public Builder<S> onConfigLoaded(Consumer<ConfigRegistry> hook) {
            this.onConfigLoaded = hook;
            return this;
        }

        /**
         * Called after the server is instantiated, before {@code setup()} runs.
         */
        public Builder<S> beforeSetup(Consumer<S> hook) {
            this.beforeSetup = hook;
            return this;
        }

        /**
         * Called after {@code setup()} completes, before {@code run()} is called.
         */
        public Builder<S> afterSetup(Consumer<S> hook) {
            this.afterSetup = hook;
            return this;
        }

        /**
         * Called after {@code run()} returns and the server is fully started.
         */
        public Builder<S> afterStartup(Consumer<S> hook) {
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
            formats.forEach(loader::addFormat);
            sources.forEach(loader::addSource);
            registerDefaults(loader);
            loader.initialize(args);

            ConfigRegistry registry = loader.asRegistry();
            onConfigLoaded.accept(registry);

            S server = serverFactory.apply(registry);
            beforeSetup.accept(server);

            server.minestomService().setup(server.auth());
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
