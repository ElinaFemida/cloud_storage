package Server;

public class Server_Main {
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 3606;
        }
        new ServerApp(port).run();

    }
}
