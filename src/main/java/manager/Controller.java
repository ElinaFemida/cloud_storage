package manager;

import dataBase.FileInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Controller implements Initializable {
    @FXML
    ListView <FileInfo> filesList;
Path root;

@FXML
    TextField pathField;


    @Override
    public void initialize(URL url, ResourceBundle resources) {
        Path root = Paths.get("cloud_storage\\1");
        changeDir (Paths.get("cloud_storage\\1"));

    }
    public void changeDir (Path path){
        root = path;
        pathField.setText(root.toAbsolutePath().toString());
        filesList.getItems().clear();
        filesList.getItems().add(new FileInfo(FileInfo.UP_ROOT, -2L));
        filesList.getItems().addAll(scanFiles(path));

    }
    public List<FileInfo > scanFiles (Path root){
        try {
            List<FileInfo> out = new ArrayList<>();
            List<Path> pathsInRoot = null;
            pathsInRoot = Files.list(root).collect(Collectors.toList());
            for (Path p : pathsInRoot){
                out.add(new FileInfo(p));
            }
            return out;

           // return (Files.list(root).map(FileInfo::new).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Something wrong with files: " + root);
        }
    }
    public void menuItemFileExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void filesListClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            FileInfo fileInfo = filesList.getSelectionModel().getSelectedItem();
            if (fileInfo != null) {
                if (fileInfo.isDir()){
Path pathTo = root.resolve(fileInfo.getName());
changeDir(pathTo);
                }
            }
            if (fileInfo.isUpElement()){
                Path pathTo = root.toAbsolutePath().getParent();
                changeDir(pathTo);
            }
        }
    }
}
