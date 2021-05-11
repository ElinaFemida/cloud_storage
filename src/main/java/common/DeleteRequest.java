package common;

public class DeleteRequest extends AbstractRequest {

    private final String fileName;

    public DeleteRequest(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}