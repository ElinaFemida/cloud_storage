package netty;

import javafx.application.Application;
        import javafx.stage.Stage;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FXApp extends Application {

    public static void main(String[] args) {
        FXApp.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}