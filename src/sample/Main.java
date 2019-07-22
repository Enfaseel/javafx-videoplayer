package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Плеер");
        primaryStage.setScene(new Scene(root, 669, 452));
        primaryStage.show();

//        primaryStage.setOnCloseRequest(event -> {
//
//        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
