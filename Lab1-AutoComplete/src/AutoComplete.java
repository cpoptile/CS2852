/*
 * Course: CS2852
 * Spring 2020
 * File header contains class AutoComplete
 * Name: poptilec
 * Created 3/11/2020
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class established a GUI for the AutoComplete program
 */
public class AutoComplete extends Application {

    /** Method creates a GUI using a FXMLLoader and given FXML file
     * @param primaryStage stage to be displayed
     * @throws Exception exceptions thrown by the program
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("autoComplete.fxml"));
        primaryStage.setTitle("Auto Complete");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
