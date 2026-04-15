package dev.minestomunited.entrypoint.config;

public interface ConfigLoader {
    ConfigLoader load(String[] args);

    <C extends Config> C get(Class<C> clazz);
}
