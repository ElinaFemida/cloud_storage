package client_of_cloud;

import common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    ListView<String> filesServerList;

    @FXML
    VBox leftPanel, rightPanel;

    @FXML
    TextField pathField;

    @FXML
    TextField Field;


    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
        ClientNetwork.stop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientNetwork.start();
    }

    public void createTextField(){
        String str = FirstController.folderName;
        Field.setText("server_for_cloud/ServerStorage/" + str);
    }

    public void DownloadFromServerBtn(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String str = FirstController.folderName;
        if (filesServerList.getSelectionModel().getSelectedItem() != null && !filesServerList.getSelectionModel().
                getSelectedItem().equals("")) {
            ClientNetwork.sendMsg(new FileRequest(filesServerList.getSelectionModel().getSelectedItem()));
            AbstractRequest request = ClientNetwork.readMsg();
            if (request instanceof FileMessage) {
                FileMessage fileMsg = (FileMessage) request;
                Files.write(Paths.get("client_for_cloud/ClientStorage/",str, fileMsg.getFileName()), fileMsg.getData(), StandardOpenOption.CREATE);
            }
        }
    }

    public static void updateUI(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    public void refreshRemoteFilesList(List<String> serverFileList) {
        updateUI(() -> {
            filesServerList.getItems().clear();
            filesServerList.getItems().addAll(serverFileList);
        });
        createTextField();
    }
    public void updateServerListBtn(ActionEvent actionEvent) {
        updateServerList();
    }

    public void updateServerList() {
        ClientNetwork.sendMsg(new FilesListRequest());
        AbstractRequest request = null;
        try {
            request = ClientNetwork.readMsg();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось обновить каталог");
            alert.showAndWait();
        }
        FilesListRequest filesListRequest = (FilesListRequest) request;
        System.out.println(filesListRequest.getRemoteFiles());
        refreshRemoteFilesList(filesListRequest.getRemoteFiles());
    }


    public void DeleteFromServerBtn(ActionEvent actionEvent) throws InterruptedException {
        if (filesServerList.getSelectionModel().getSelectedItem() != null && !filesServerList.
                getSelectionModel().
                getSelectedItem().
                equals("")) {
            ClientNetwork.sendMsg(new DeleteRequest(filesServerList.getSelectionModel().getSelectedItem()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION,  "Файл: " + filesServerList
                    .getSelectionModel()
                    .getSelectedItem() +" удален на сервере");
            alert.showAndWait();
            updateServerList();
        }
    }

}