package common;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class FileInfo {
    public static final String UP_ROOT = "[..]";
    private String name;
    private long size;
    private LocalDateTime date;


    public FileInfo(String name, long size) {
        this.name = name;
        this.size = size;
        this.date = date;

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
            this.date = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));
        } catch (IOException e) {
            throw new RuntimeException("Something wrong with file " + path.toAbsolutePath().toString());
        }
    }

    public boolean isDir() {
        return size == -1L;
    }

    public boolean isUpElement() {
        return size == -2L;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
