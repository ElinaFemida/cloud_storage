package client_of_cloud;


import common.AbstractRequest;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    private static Socket socket;
    private static ObjectEncoderOutputStream out;
    public static ObjectDecoderInputStream in;

    public static void start() {
        try {
            socket = new Socket("localhost", 3606);
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            in = new ObjectDecoderInputStream(socket.getInputStream(), 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean sendMsg(AbstractRequest msg) {
        try {

            out.writeObject(msg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static AbstractRequest readMsg() throws ClassNotFoundException, IOException{
        Object obj = in.readObject();
        return (AbstractRequest) obj;
    }
}







