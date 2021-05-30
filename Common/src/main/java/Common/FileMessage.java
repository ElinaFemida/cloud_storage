package Common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMessage extends AbstractRequest {

    private static final long serialVersionUID = Long.MAX_VALUE;

    private final String fileName;
    private final byte[] data;
    private final double size;
    private final String md;

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public double getSize() {
        return size;
    }

    public String getMd() {
        return md;
    }

    public FileMessage(Path path) throws IOException, NoSuchAlgorithmException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);
        size = Files.size(path);
        md = getCheckSum();
    }

    private String getCheckSum() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data);
        byte[] hashByte = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashByte) sb.append(String.format("%08X", b));
        return sb.toString();
    }
}