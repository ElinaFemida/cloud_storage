package common;


import java.util.ArrayList;
import java.util.List;

public class FilesListRequest extends AbstractRequest {

    private final List<String> remoteFiles;

    public List<String> getRemoteFiles() {
        return remoteFiles;
    }

    public void addFile(String fileName) {
        remoteFiles.add(fileName);
    }

    public FilesListRequest() {
        this.remoteFiles = new ArrayList<>();
    }

}