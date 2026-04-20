package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppFactory factory = new AppFactory();
        factory.initUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}