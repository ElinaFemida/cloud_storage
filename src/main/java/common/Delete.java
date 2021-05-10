package common;

public class Delete extends Commands {
    private String fileName;

    public String getFilename() {
        return fileName;
    }

    public Delete(String filename) {
        this.fileName = filename;
        this.command = Command.DELETE;
    }
}