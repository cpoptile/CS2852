/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class Picture
 * Name: poptilec
 * Created 3/31/2020
 */

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Picture class handles creation of image on the canvas
 */
public class Picture {

    private List<Dot> dotsList;
    /**
     * double represents the factor by which the coordinates of each dot
     * is multiplied by in order to better fit the canvas
     */
    private static final double DOT_FACTOR = 1000.0;
    /**
     * double represents the diameter of each dot
     */
    private static final double DOT_DIAMETER = 10.0;

    /** Constructor creates a Picture object with specified list to be used
     * @param emptyList type of list to be used to store Dots
     */
    public Picture(List<Dot> emptyList){
        dotsList = emptyList;
    }

    /** Constructor creates a Picture object from an original Picture object and specified list
     * @param original original Picture object
     * @param emptyList list to be used
     */
    public Picture(Picture original, List<Dot> emptyList){
        emptyList.addAll(original.dotsList);
        dotsList = emptyList;
    }

    /** Method loads all dots from .dot file and store each dot into an ArrayList
     * @param path path of .dot file
     * @throws IllegalArgumentException exception for incorrect file extension
     * @throws OutOfBoundsException exception when coordinates are greater than 1
     * @throws FileNotFoundException exception for when a file is not found
     */
    public void load(Path path) throws IllegalArgumentException,
            OutOfBoundsException, FileNotFoundException {
        if (path.toString().endsWith(".dot")) {
            try (Scanner scan = new Scanner(path.toFile())) {
                if (dotsList.getClass().toString().equals("class java.util.LinkedList")){
                    dotsList = new LinkedList<>();
                } else {
                    dotsList = new ArrayList<>();
                }
                while (scan.hasNextLine()) {
                    String fileLine = scan.nextLine();
                    String[] dimensions = fileLine.split("[,\\s]+");
                    double x = Double.parseDouble(dimensions[0]);
                    double y = Double.parseDouble(dimensions[1]);
                    if (x>1.0 || y>1.0){
                        throw new OutOfBoundsException("Coordinates out of bounds");
                    }
                    Dot dot = new Dot(x * DOT_FACTOR, y * DOT_FACTOR);
                    dotsList.add(dot);
                }
            }
        } else{
            throw new IllegalArgumentException();
        }
    }

    /** Method draws dots on the canvas
     * @param canvas canvas to be drawn on
     */
    public void drawDots(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Dot dot : dotsList) {
            gc.setFill(Color.BLACK);
            gc.fillOval(dot.getX() - (DOT_DIAMETER / 2), dot.getY() - (DOT_DIAMETER / 2),
                    DOT_DIAMETER, DOT_DIAMETER);
        }
    }

    /** Method draws lines onto the canvas
     * @param canvas canvas to be drawn on
     */
    public void drawLines(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(dotsList.get(0).getX(), dotsList.get(0).getY());
        for (int index = 1; index < dotsList.size(); index++) {
            gc.lineTo(dotsList.get(index).getX(), dotsList.get(index).getY());
        }
        gc.closePath();
        gc.stroke();

    }

    /** Methods saves the current image on the canvas as a .dot file
     * @param path path of file to be saved
     * @throws IOException
     */
    public void save(Path path) throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(path.toFile()))){
            for (Dot dot : dotsList) {
                String line = dot.getX() + "" + dot.getY();
                writer.println(line);
            }
        }
    }

    /** Method removes amount of dots inputted by user
     * @param numberDesired number of dots desired to be removed
     * @throws IllegalArgumentException exception for when desired number of dots
     * to be removed is less than 3
     */
    public void removeDots(int numberDesired) throws IllegalArgumentException{
        if (numberDesired<3){
            throw new IllegalArgumentException();
        } else {
            while (dotsList.size() > numberDesired) {
                double lowestCriticalValue = 10000;
                int removeIndex = 0;
                double criticalValue = dotsList.get(0).calculateCriticalValue(dotsList.get(dotsList.size()-1), dotsList.get(1));
                if (criticalValue <= lowestCriticalValue){
                    lowestCriticalValue = criticalValue;
                    removeIndex = 0;
                }
                for (int index = 1; index < dotsList.size() - 1; index++) {
                    Dot current = dotsList.get(index);
                    criticalValue = current.calculateCriticalValue(dotsList.get(index - 1), dotsList.get(index + 1));
                    if (criticalValue <= lowestCriticalValue) {
                        lowestCriticalValue = criticalValue;
                        removeIndex = index;
                    }
                }
                criticalValue = dotsList.get(dotsList.size()-1).calculateCriticalValue(dotsList.get(dotsList.size()-2), dotsList.get(0));
                if (criticalValue<= lowestCriticalValue){
                    removeIndex = dotsList.size()-1;
                }
                dotsList.remove(removeIndex);
            }

        }

    }

    public int getSize(){
        return dotsList.size();
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

}
