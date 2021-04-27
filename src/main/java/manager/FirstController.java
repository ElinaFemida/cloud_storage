package manager;

import client_of_cloud.ClientNetwork;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class FirstController implements Initializable {
    private ClientNetwork network;

    public void authorize(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new ClientNetwork();
    }
}
