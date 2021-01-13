/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class DotToDotController
 * Name: poptilec
 * Created 3/20/2020
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Paths;

/**
 * Controller class for the Dot To Dot program
 */
public class DotToDotController {

    @FXML
    private Canvas canvas;
    @FXML
    private MenuItem dotsOnlyOption;
    @FXML
    private MenuItem linesOnlyOption;

    /**
     * Method loads in given .dot file
     */
    @FXML
    private void open(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("File Chooser");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                Picture.load(Paths.get(file.getPath()));
                Picture.drawDots(canvas);
                Picture.drawLines(canvas);
                dotsOnlyOption.setDisable(false);
                linesOnlyOption.setDisable(false);
            } catch (IllegalArgumentException e){
                Alert extension = new Alert(Alert.AlertType.ERROR, "File type is not supported");
                extension.showAndWait();
            } catch (Picture.OutOfBoundsException e){
                Alert outOfBounds = new Alert(Alert.AlertType.ERROR,
                        "Coordinates are out of bounds");
                outOfBounds.showAndWait();
            } catch (Picture.MissingDataException e){
                Alert outOfBounds = new Alert(Alert.AlertType.ERROR,
                        "Data from file cannot be properly loaded");
                outOfBounds.showAndWait();
            }
        } else {
            Alert noFileChosen = new Alert(Alert.AlertType.ERROR,
                    "Please select a file");
            noFileChosen.showAndWait();
        }
    }

    /**
     * Method draws image on canvas using only dots
     */
    @FXML
    private void dotsOnly(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Picture.drawDots(canvas);
    }

    /**
     * Method draws image on canvas using only lines
     */
    @FXML
    private void linesOnly(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Picture.drawLines(canvas);

    }

    /**
     * Method exits the program
     */
    @FXML
    private void exit(){
        Platform.exit();
    }


}
