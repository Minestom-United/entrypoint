package dev.minestomunited.entrypoint;

public class DemoMain {

    static void main(String[] args) {
        EntryPoint
                .builder()
                .server(DemoServer::new)
                .run(args);
    }
}
