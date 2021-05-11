package client_of_cloud;

import common.Auth;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstController {
    @FXML
    TextField login;

    @FXML
    TextField password;

    public static String getFolderName() {
        return folderName;
    }

    public static String folderName;

    @FXML
    VBox globParent;

    @FXML
    Label errorLabel;

    public void authorize() throws IOException {
        if (login.getText().equalsIgnoreCase("login") && password.getText().equalsIgnoreCase("password")) {
        ClientNetwork.start();
        ClientNetwork.sendMsg(new Auth(login.getText()));
        folderName = login.getText();
        System.out.println(folderName);
        }
        globParent.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        Stage newWindow = new Stage();
        newWindow.setScene(new Scene(root, 800, 600));
        newWindow.show();
        FileWatcher fileWatcher = new FileWatcher();
        fileWatcher.dirMonitorThread = new Thread(new FileWatcher());
    }
    public void stop (Stage stage){
        stage.setOnCloseRequest(e -> stage.close());
    }
}
