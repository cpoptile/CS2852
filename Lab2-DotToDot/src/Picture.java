/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class Picture
 * Name: poptilec
 * Created 3/20/2020
 */

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Picture class handles creation of image on the canvas
 */
public class Picture {

    private static List<Dot> dotsList = new ArrayList<>();
    /**
     * double represents the factor by which the coordinates of each dot
     * is multiplied by in order to better fit the canvas
     */
    private static final double DOT_FACTOR = 1000.0;
    /**
     * double represents the diameter of each dot
     */
    private static final double DOT_DIAMETER = 10.0;

    /** Method loads all dots from .dot file and store each dot into an ArrayList
     * @param path path of .dot file
     * @throws IllegalArgumentException exception for incorrect file extension
     * @throws OutOfBoundsException exception when coordinates are greater than 1
     * @throws MissingDataException exception for either an empty file or when data was not
     * properly loaded from the file
     */
    public static void load(Path path) throws IllegalArgumentException,
            OutOfBoundsException, MissingDataException {
        if (path.toString().endsWith(".dot")) {
            try (Scanner scan = new Scanner(path.toFile())) {
                List<String> fileLines = new ArrayList<>();
                while (scan.hasNextLine()) {
                    fileLines.add(scan.nextLine());
                }
                if (fileLines.size()==0){
                    throw new MissingDataException("Data from file not found");
                }
                for (String fileLine : fileLines) {
                    String[] dimensions = fileLine.split("[,\\s]+");
                    double x = Double.parseDouble(dimensions[0]);
                    double y = Double.parseDouble(dimensions[1]);
                    if (x>1.0 || y>1.0){
                        throw new OutOfBoundsException("Coordinates out of bounds");
                    }
                    Dot dot = new Dot(x * DOT_FACTOR, y * DOT_FACTOR);
                    dotsList.add(dot);
                }
            } catch (FileNotFoundException e) {
                Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "File was not found");
                fileNotFound.showAndWait();
            } catch (NumberFormatException e) {
                Alert incorrectData = new Alert(Alert.AlertType.ERROR,
                        "File contains incorrectly formatted data");
                incorrectData.showAndWait();
            }
        } else{
            throw new IllegalArgumentException();
        }
    }

    /** Method draws dots on the canvas
     * @param canvas canvas to be drawn on
     */
    public static void drawDots(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Dot dot: dotsList){
            gc.setFill(Color.BLACK);
            gc.fillOval(dot.getX()-(DOT_DIAMETER/2), dot.getY()-(DOT_DIAMETER/2),
                    DOT_DIAMETER, DOT_DIAMETER);
        }

    }

    /** Method draws lines onto the canvas
     * @param canvas canvas to be drawn on
     */
    public static void drawLines(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(dotsList.get(0).getX(), dotsList.get(0).getY());
        for (int index = 1; index<dotsList.size(); index++){
            gc.lineTo(dotsList.get(index).getX(), dotsList.get(index).getY());
        }
        gc.closePath();
        gc.stroke();

    }

    /**
     * Exception class for when a coordinate is out of bounds
     */
    public static class OutOfBoundsException extends Exception {
        /** Constructor for OutOfBoundsException
         * @param errorMessage message to be displayed to console
         */
        public OutOfBoundsException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * Exception class for when data is either not present or properly
     * loaded from a file
     */
    public static class MissingDataException extends Exception {
        /** Constructor for MissingDataException
         * @param errorMessage message to be displayed to console
         */
        public MissingDataException(String errorMessage) {
            super(errorMessage);
        }
    }

}
