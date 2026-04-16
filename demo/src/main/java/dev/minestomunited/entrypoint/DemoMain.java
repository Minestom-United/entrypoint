package dev.minestomunited.entrypoint;

public class DemoMain {

    public static void main(String[] args) {
        EntryPoint.run(args, DemoServer::new);
    }
}
