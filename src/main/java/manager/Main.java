package manager;

import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cloud_storage.fxml"));
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/first_window.fxml"));
        Parent root = fxmlLoader.load();
        //Controller controller = fxmlLoader.getController();
        FirstController controller = fxmlLoader.getController();
        primaryStage.setTitle("File manager");
        primaryStage.setScene(new Scene(root, 300, 160));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}