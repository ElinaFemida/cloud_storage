package common;

public class FileRequest extends AbstractRequest {
    private final String fileName;

    public String getFileName() {
        return fileName;
    }

    public FileRequest(String fileName) {
        this.fileName = fileName;
    }
}