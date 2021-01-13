/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class DotToDot
 * Name: poptilec
 * Created 3/31/2020
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class creates GUI with FXML and links controller class
 */
public class DotToDot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("dotToDot.fxml"));
        primaryStage.setTitle("Dot to Dot");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
