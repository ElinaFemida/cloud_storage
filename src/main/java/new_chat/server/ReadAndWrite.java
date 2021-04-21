package new_chat.server;

import java.io.FileNotFoundException;

public interface ReadAndWrite extends AutoCloseable {

    void close() throws FileNotFoundException;

}
