package common;

import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;

public class FileMessage extends AbstractRequest {

    private static final long serialVersionUID = Long.MAX_VALUE;

    private final String fileName;
    private final byte[] data;
    private final double size;

    public double getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public FileMessage(Path path) throws IOException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);
        size = Files.size(path);
    }
}