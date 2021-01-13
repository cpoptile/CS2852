/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class DotToDotController
 * Name: poptilec
 * Created 3/31/2020
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    @FXML
    private TextField numberOfDots;

    private Picture picture;
    private List<Dot> dotList;

    /**
     * Method loads in given .dot file
     */
    @FXML
    public void open(){
        try {
            if (dotList.isEmpty()) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("File Chooser");
                File file = chooser.showOpenDialog(null);
                if (file != null) {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    picture = new Picture(dotList);
                    try {
                        picture.load(Paths.get(file.getPath()));
                        picture.drawDots(canvas);
                        picture.drawLines(canvas);
                        dotsOnlyOption.setDisable(false);
                        linesOnlyOption.setDisable(false);
                        updateDotNumber(picture);
                    } catch (NumberFormatException e){
                        Alert numberFormat = new Alert(Alert.AlertType.ERROR,
                                "Numbers are incorrectly formatted");
                        numberFormat.showAndWait();
                    } catch (NullPointerException e){
                        Alert nullPointer = new Alert(Alert.AlertType.ERROR,
                                "File is null");
                        nullPointer.showAndWait();
                    } catch (IllegalArgumentException e) {
                        Alert extension = new Alert(Alert.AlertType.ERROR,
                                "File type is not supported");
                        extension.showAndWait();
                    } catch (Picture.OutOfBoundsException e) {
                        Alert outOfBounds = new Alert(Alert.AlertType.ERROR,
                                "Coordinates are out of bounds");
                        outOfBounds.showAndWait();
                    } catch (FileNotFoundException e) {
                        Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "File was not found");
                        fileNotFound.showAndWait();
                    }
                } else {
                    Alert noFileChosen = new Alert(Alert.AlertType.ERROR,
                            "Please select a file");
                    noFileChosen.showAndWait();
                }
            }
        } catch (NullPointerException e){
            Alert noStrategy = new Alert(Alert.AlertType.ERROR,
                    "Please select a strategy");
            noStrategy.showAndWait();
        }
    }

    /**
     * Method draws image on canvas using only dots
     */
    @FXML
    public void dotsOnly(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        picture.drawDots(canvas);
    }

    /**
     * Method draws image on canvas using only lines
     */
    @FXML
    public void linesOnly(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        picture.drawLines(canvas);

    }

    /**
     * Method exits the program
     */
    @FXML
    public void exit(){
        Platform.exit();
    }

    /**
     * Method sets list to be used by the Picture class as an ArrayList
     */
    @FXML
    public void arrayListStrategy(){
        dotList = new ArrayList<>();
    }

    /**
     * Method sets list to be used by the Picture class as a LinkedList
     */
    @FXML
    public void linkedListStrategy(){
        dotList = new LinkedList<>();
    }

    /**
     * Method updates canvas image with amount of dots specified by user
     */
    @FXML
    public void updateNumberOfDots(){
        try {
            List<Dot> newList = dotList;
            newList.clear();
            Picture newPicture = new Picture(picture, newList);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            newPicture.removeDots(Integer.parseInt(numberOfDots.getText()));
            newPicture.drawLines(canvas);
            newPicture.drawDots(canvas);
            updateDotNumber(newPicture);
        } catch (IllegalArgumentException e){
            Alert lowDots = new Alert(Alert.AlertType.ERROR,
                    "The number of desired dots is too low");
            lowDots.showAndWait();
        }
    }

    /** Method
     * @param picture picture object to be evaluated
     */
    public void updateDotNumber(Picture picture){
        numberOfDots.setText(picture.getSize()+"");
    }

    /**
     * Method saves image on canvas as a .dot file
     */
    @FXML
    public void save(){
        FileChooser saver = new FileChooser();
        saver.setTitle("file chooser");
        saver.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot file", "*.dot"));
        File file = saver.showSaveDialog(null);
        try {
            picture.save(file.toPath());
        } catch(IllegalArgumentException e){
            Alert extension = new Alert(Alert.AlertType.ERROR, "file type not supported");
            extension.showAndWait();
        } catch (IOException e){
            Alert ioException = new Alert(Alert.AlertType.ERROR, "An IO exception has occurred");
            ioException.showAndWait();
        }
    }


}
