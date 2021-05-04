package manager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            primaryStage.setTitle("Files storage");
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(400);
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
