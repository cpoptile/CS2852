/*
 * Course: CS2852
 * Spring 2020
 * File header contains class AutoCompleteController
 * Name: poptilec
 * Created 3/11/2020
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Controller class to handle interaction with the AutoComplete GUI
 */
public class AutoCompleteController {
    // FXML Nodes to be altered
    @FXML
    private Label timeLabel;
    @FXML
    private Label matches;
    @FXML
    private TextField searchBox;
    @FXML
    private TextArea matchesBox;
    @FXML
    private RadioMenuItem iaSelection;
    @FXML
    private RadioMenuItem ilSelection;
    @FXML
    private RadioMenuItem eaSelection;
    @FXML
    private RadioMenuItem elSelection;

    private File dictionaryFile;
    /*Strategy to be used:
    1 = ArrayList navigated using indexing methods
    2 = LinkedList navigated using indexing methods
    3 = ArrayList navigated using for-each loops
    4 = LinkedList navigated using for-each loops
     */
    private int strategy = 0;

    private List<String> matchesArray = new ArrayList<>();
    private List<String> matchesLinked = new LinkedList<>();

    //create instances of each strategy to be used
    private IndexedArrayList indexedArray = new IndexedArrayList();
    private IndexedLinkedList indexedLinked = new IndexedLinkedList();
    private EnhancedArrayList enhancedArray = new EnhancedArrayList();
    private EnhancedLinkedList enhancedLinked = new EnhancedLinkedList();


    /**
     * Method allows user to select a file that will be used to find matches
     * @throws IllegalArgumentException exception thrown when file is not .txt or .csv
     * @throws NullPointerException exception when file is null or contains nothing
     */
    @FXML
    private void load() throws IllegalArgumentException, NullPointerException{
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Dictionary File");
            dictionaryFile = chooser.showOpenDialog(null);
            if (dictionaryFile == null){
                throw new NullPointerException("No File Chosen");
            } else if (!(dictionaryFile.toString().endsWith(".txt"))
                    && !(dictionaryFile.toString().endsWith(".csv"))){
                throw new IllegalArgumentException("Unsupported file");
            } else if (dictionaryFile.length() == 0 ) {
                throw new NullPointerException("Empty File");
            }
        } catch (IllegalArgumentException e){
            Alert extensionError = new Alert(Alert.AlertType.ERROR,
                    "Please select a .txt or .csv file");
            extensionError.setHeaderText("Unsupported File Type");
            extensionError.showAndWait();
        } catch (NullPointerException e){
            if (e.getMessage().equals("Empty File")){
                Alert emptyFile = new Alert(Alert.AlertType.ERROR,
                        "The file chosen has no data");
                emptyFile.setHeaderText("Empty File Chosen");
                emptyFile.showAndWait();
            } else {
                Alert noFile = new Alert(Alert.AlertType.ERROR, "Please select a dictionary file");
                noFile.setHeaderText("No File Chosen");
                noFile.showAndWait();
            }
        }
    }

    /**
     * Method updates and searches the given dictionary file using
     * the selected strategy
     */
    @FXML
    private void typing() {
        if (dictionaryFile == null) {
            Alert noFile = new Alert(Alert.AlertType.ERROR, "Please load in a dictionary file");
            noFile.setHeaderText("No Dictionary File Loaded");
            noFile.showAndWait();
            searchBox.clear();
        } else if (strategy == 0){
            Alert noStrategy = new Alert(Alert.AlertType.ERROR, "Please select a strategy");
            noStrategy.setHeaderText("No Strategy Selected");
            noStrategy.showAndWait();
            searchBox.clear();
        } else if (strategy == 1) {
            matchesArray = indexedArray.allThatBeginWith(searchBox.getText());
            matches.setText(Integer.toString(matchesArray.size()));
        } else if (strategy == 2) {
            matchesLinked = indexedLinked.allThatBeginWith(searchBox.getText());
            matches.setText(Integer.toString(matchesLinked.size()));
        } else if (strategy == 3) {
            matchesArray = enhancedArray.allThatBeginWith(searchBox.getText());
            matches.setText(Integer.toString(matchesArray.size()));
        } else if (strategy == 4) {
            matchesLinked = enhancedLinked.allThatBeginWith(searchBox.getText());
            matches.setText(Integer.toString(matchesLinked.size()));
        }
        setTime();
        setMatches();
    }

    /**
     * Sets the strategy to be used by storing words in an ArrayList
     * and using index methods to navigate through it
     */
    @FXML
    private void setIndexedArray() {
        strategy = 1;
        ilSelection.setSelected(false);
        eaSelection.setSelected(false);
        elSelection.setSelected(false);
        indexedArray.initialize(dictionaryFile.toString());
        setTime();
    }

    /**
     * Sets the strategy to be used by storing words in an LinkedList
     * and using index methods to navigate through it
     */
    @FXML
    private void setIndexedLinked() {
        strategy = 2;
        iaSelection.setSelected(false);
        eaSelection.setSelected(false);
        elSelection.setSelected(false);
        indexedLinked.initialize(dictionaryFile.toString());
        setTime();
    }

    /**
     * Sets the strategy to be used by storing words in an ArrayList
     * and using enhanced for-loops to navigate through it
     */
    @FXML
    private void setEnhancedArray() {
        strategy = 3;
        iaSelection.setSelected(false);
        ilSelection.setSelected(false);
        elSelection.setSelected(false);
        enhancedArray.initialize(dictionaryFile.toString());
        setTime();
    }

    /**
     * Sets the strategy to be used by storing words in an LinkedList
     * and using enhanced for-loops to navigate through it
     */
    @FXML
    private void setEnhancedLinked() {
        strategy = 4;
        iaSelection.setSelected(false);
        ilSelection.setSelected(false);
        eaSelection.setSelected(false);
        enhancedLinked.initialize(dictionaryFile.toString());
        setTime();
    }

    /**
     * Method displays time of each operation with selected strategy
     */
    private void setTime(){
        double time = 0;
        final int nsInMicroseconds = 1000;
        final int nsInMilliseconds = 1000000;
        final int nsInSeconds = 1000000000;
        final long nsInMinutes = 1000000000000L;
        DecimalFormat df = new DecimalFormat("0.000");

        timeLabel.setText("");

        if (strategy == 1) {
            time = indexedArray.getLastOperationTime();
        } else if (strategy == 2) {
            time = indexedLinked.getLastOperationTime();
        } else if (strategy == 3) {
            time = enhancedArray.getLastOperationTime();
        } else if (strategy == 4) {
            time = enhancedLinked.getLastOperationTime();
        }

        if (time < nsInMicroseconds) {
            timeLabel.setText(df.format(time) + " nanoseconds");
        } else if ((time < nsInMilliseconds) && (time > nsInMicroseconds)) {
            timeLabel.setText(df.format(time / nsInMicroseconds)
                    + " microseconds");
        } else if ((time < nsInSeconds) && (time > nsInMilliseconds)) {
            timeLabel.setText(df.format(time / nsInMilliseconds)
                    + " milliseconds");
        } else {
            int minutes = (int) (time / nsInMinutes);
            int seconds = (int) ((time - (minutes * nsInMinutes)) / nsInSeconds);
            int milliseconds = (int) ((time - (seconds * nsInSeconds)) / nsInMilliseconds);
            timeLabel.setText(df.format(minutes) + ":" + df.format(seconds)
                    + "." + df.format(milliseconds));
        }
    }

    /**
     * Method displays all matches found in the TextArea on the GUI
     */
    private void setMatches(){
        matchesBox.setText("");
        if ((strategy == 1) || (strategy == 3)) {
            for (String word : matchesArray) {
                matchesBox.appendText(word + "\n");
            }
        } else {
            for (String word : matchesLinked) {
                matchesBox.appendText(word + "\n");
            }
        }
    }


}
