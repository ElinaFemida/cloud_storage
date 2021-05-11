package client_of_cloud;

import java.io.IOException;
import java.nio.file.*;


public class FileWatcher implements Runnable {

    protected Thread dirMonitorThread;
    private WatchService watchService;
    private WatchKey watchKey;
    private MainController controller;


    public Thread getDirMonitorThread() {
        return dirMonitorThread;
    }

    public void setDirMonitorThread(Thread dirMonitorThread) {
        this.dirMonitorThread = dirMonitorThread;
    }

    public WatchService getWatchService() {
        return watchService;
    }

    public void setWatchService(WatchService watchService) {
        this.watchService = watchService;
    }

    public WatchKey getWatchKey() {
        return watchKey;
    }

    public void setWatchKey(WatchKey watchKey) {
        this.watchKey = watchKey;
    }

    public MainController getController() {
        return controller;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }


    void register(String directoryPath) throws IOException, ClassNotFoundException {
        System.out.println(directoryPath);
        Path folder = Paths.get(directoryPath);
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e1) {
            System.out.println("directoryPath NOT found");
            e1.printStackTrace();
        }
        try {
            folder.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            watchKey = watchService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startWatcher(directoryPath);
    }

    public void startWatcher(String directoryPath) throws IOException, ClassNotFoundException {
        boolean valid = true;
        do {
            for (WatchEvent event : watchKey.pollEvents()) {
                event.kind();
                String eventType = "";
                String tempPath = directoryPath;
                String fileName = "";
                if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                    fileName = event.context().toString();
                    System.out.println("File Created:" + fileName
                            + ", EventKind : " + event.kind());
                    eventType = event.kind().toString();
                } else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event
                        .kind())) {
                    fileName = event.context().toString();
                    System.out.println("File Deleted:" + fileName
                            + ", EventKind : " + event.kind());
                    eventType = event.kind().toString();
                }
            }
            valid = watchKey.reset();
        } while (valid);
    }

    public void run() {
            String name = FirstController.getFolderName();
            FileWatcher fileWatcher = null;
            fileWatcher = new FileWatcher();
            String directoryPath = "server_for_cloud/ServerStorage/" + name;
            try {
                fileWatcher.register(directoryPath);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
}