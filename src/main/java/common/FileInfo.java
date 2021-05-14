package common;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo extends AbstractRequest {
    public enum FileType{
        FILE("F"), DIRECTORY("D");

        private final String TypeName;

        public String getTypeName(){
            return TypeName;
        }

        FileType(String name){
            this.TypeName = name;
        }

    }

    private String fileName;
    private FileType fileType;
    private long fileSize;
    private LocalDateTime fileDate;

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public FileType getFileType(){
        return fileType;
    }

    public void setType(){
        this.fileType = fileType;
    }

    public long getFileSize(){
        return fileSize;
    }

    public void setSize(){
        this.fileSize = fileSize;
    }

    public LocalDateTime getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    public FileInfo(Path path){
        try {
            this.fileName = path.getFileName().toString();
            this.fileSize = Files.size(path);
            this.fileType = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            if (this.fileType == FileType.DIRECTORY){
                this.fileSize = -1L;
            }
            this.fileDate = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
