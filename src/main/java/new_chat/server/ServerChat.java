package new_chat.server;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerChat implements Chat {
    private ServerSocket serverSocket;
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;
    static Logger LOGGER;
    static {
        try{
            LogManager.getLogManager().readConfiguration();
            LOGGER = Logger.getLogger(ServerChat.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public ServerChat() {
        start();
    }


    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(8888);
            clients = new HashSet<>();
            authenticationService = new AuthenticationService();
            LOGGER.log(Level.INFO,"Server is waiting for a connection ...");

            while (true) {
                //System.out.println("Server is waiting for a connection ...");
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                LOGGER.log(Level.INFO, "New client is successfully logged in");
                //System.out.println(String.format("[%s] Client[%s] is successfully logged in", new Date(), clientHandler.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    @Override
    public synchronized boolean isNicknameOccupied(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
}
