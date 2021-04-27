package dataBase;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileInfo {
    public static final String UP_ROOT = "[..]";
    private String name;
    private long size;

    public FileInfo(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileInfo(Path path) {
        try {
            this.name = path.getFileName().toString();
            if (Files.isDirectory(path)) {
                this.size = -1L;
            } else {
                this.size = Files.size(path);
            }
        }
            catch(IOException e){
                throw new RuntimeException("Something wrong with file " + path.toAbsolutePath().toString());
            }
        }
        public boolean isDir () {
        return size == -1L;
        }

        public boolean isUpElement (){
        return size == -2L;
        }
    }
