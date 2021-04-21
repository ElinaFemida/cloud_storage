package new_chat.server;

import java.io.*;

public class ChatHistory implements ReadAndWrite{

    private  static PrintWriter printOut;

    private ChatHistory (PrintWriter printOut) {
        this.printOut = printOut;
    }


    public static ChatHistory getInstance(File file) throws FileNotFoundException {
        try {
            return new ChatHistory(new PrintWriter(new FileOutputStream(file, true), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close(){
        if(printOut != null){
            printOut.close();
        }
    }


    public static void writeToFile(String message) {
        printOut.println(message);
    }


}