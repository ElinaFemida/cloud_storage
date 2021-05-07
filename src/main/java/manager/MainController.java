package manager;

import common.FileInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class MainController implements Initializable {
    @FXML
    ListView<FileInfo> filesList;
    Path root;

    @FXML
    TextField pathField;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        root = Paths.get("cloud_storage\\files");
        filesList.getItems().addAll(scanFiles(root));


        filesList.setCellFactory(new Callback<ListView<FileInfo>, ListCell<FileInfo>>() {
            @Override
            public ListCell<FileInfo> call(ListView<FileInfo> fileInfoListView) {
                return new ListCell<FileInfo>() {
                    @Override
                    protected void updateItem(FileInfo item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            String formattedFilename = String.format("%-30s", item.getName());
                            String formattedFilesize = String.format("// %,d bytes", item.getSize());
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(": yyyy-MM-dd HH:mm:ss");
                            String formattedDate = dtf.toString();
                            if (item.getSize() == -1L) {
                                formattedFilesize = String.format("%s", "DIR");
                            }
                            if (item.getSize() == -2L) {
                                formattedFilesize = String.format("");
                            }
                            String text = String.format("%30s %s %s", formattedFilename, formattedFilesize, formattedDate);
                            setText(text);
                        }
                    }
                };
            }
        });
    }

    public void changeDir(Path path) {
        root = path;
        pathField.setText(root.toAbsolutePath().toString());
        filesList.getItems().clear();
        filesList.getItems().add(new FileInfo(FileInfo.UP_ROOT, -2L));
        filesList.getItems().addAll(scanFiles(path));
        filesList.getItems().sort(new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                if (o1.getName().equals(FileInfo.UP_ROOT)) {
                    return -1;
                }
                if ((int) Math.signum(o1.getSize()) == (int) Math.signum(o2.getSize())) {
                    return o1.getName().compareTo(o2.getName());
                }
                return Long.valueOf(o1.getSize() - o2.getSize()).intValue();
            }
        });

    }

    public List<FileInfo> scanFiles(Path root) {
        try {
            return (Files.list(root).map(FileInfo::new).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Something wrong with files: " + root);
        }
    }

    public void FilesListClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileInfo fileInfo = filesList.getSelectionModel().getSelectedItem();
            if (fileInfo != null) {
                if (fileInfo.isDir()) {
                    Path pathTo = root.resolve(fileInfo.getName());
                    changeDir(pathTo);
                }
            }
            if (fileInfo.isUpElement()) {
                Path pathTo = root.toAbsolutePath().getParent();
                changeDir(pathTo);
            }
        }
    }

    public void auth(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/first_window.fxml"));
            stage.setTitle("Войти или зарегистрироваться");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();
            stage.setOnCloseRequest(e -> stage.close());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeDir(ActionEvent actionEvent) {
    }

    public String getSelectedFilename() {
        if (!filesList.isFocused()) {
            return null;
        }
        return filesList.getSelectionModel().getSelectedItem().getName();
    }

    public void sync (ActionEvent actionEvent) {

    }

    public void delete(MouseEvent actionEvent) {
        String deletedFile = getSelectedFilename().toString();
        System.out.println(deletedFile);
        try {
            Files.delete(Paths.get(deletedFile));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String getCurrentPath() {
        return pathField.getText();
    }
}
