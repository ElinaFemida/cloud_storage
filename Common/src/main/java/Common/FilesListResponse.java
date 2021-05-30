package Common;

import java.util.List;

public class FilesListResponse extends AbstractRequest {

    public List<FileInfo> files;

    public FilesListResponse() {
    }

    public FilesListResponse(List<FileInfo> files) {
        this.files = files;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
