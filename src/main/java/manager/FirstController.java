package manager;


import client_of_cloud.ClientNetwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstController implements Initializable {
    private ClientNetwork network;

    @FXML
    TextField login;

    @FXML
    TextField password;

    @FXML
    Button auth;

    public void authorize(ActionEvent actionEvent) {
        if (login.getText().equalsIgnoreCase("login") && password.getText().equalsIgnoreCase("password")) {
            network = new ClientNetwork();
            authNotify(actionEvent);
            ((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).close();
        }
    }

    public void register(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void authNotify(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/auth_ok_window.fxml"));
            stage.setTitle("Attention");
            stage.setMinHeight(100);
            stage.setMinWidth(200);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();
            stop(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop (Stage stage){
        stage.setOnCloseRequest(e -> stage.close());
    }
}
