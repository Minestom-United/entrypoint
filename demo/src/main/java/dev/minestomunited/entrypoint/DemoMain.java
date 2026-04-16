package dev.minestomunited.entrypoint;

public class DemoMain {

    static void main(String[] args) {
        EntryPoint.run(args, DemoServer::new);
    }
}
