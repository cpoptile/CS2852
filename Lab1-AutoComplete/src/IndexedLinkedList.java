/*
 * Course: CS2852
 * Spring 2020
 * File header contains class AutoComplete
 * Name: poptilec
 * Created 3/11/2020
 */

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class represents a strategy selected to find words with a given prefix in a file
 * Words from the selected file are stored in a LinkedList and uses index methods
 * to navigate through that list
 */
public class IndexedLinkedList implements AutoCompleter {

    private List<String> dictionaryList = new LinkedList<>();
    private double startTime;
    private double endTime;
    private boolean initialized = false;

    @Override
    public void initialize(String filename) {
        startTime = System.nanoTime();
        File dictionaryFile = new File(filename);
        try (Scanner scan = new Scanner(dictionaryFile)) {
            while (scan.hasNextLine()) {
                if (filename.endsWith(".csv")) {
                    String line = scan.nextLine();
                    String text = line.substring(line.indexOf(',') + 1);
                    dictionaryList.add(text);
                } else {
                    dictionaryList.add(scan.nextLine());
                }
            }
            initialized = true;
        } catch (FileNotFoundException e) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "File Not Found");
            fileNotFound.showAndWait();
        } catch (NullPointerException e){
            Alert nullPointer = new Alert(Alert.AlertType.ERROR, "Null Pointer Exception");
            nullPointer.showAndWait();
        }
        endTime = System.nanoTime();
    }

    @Override
    public List<String> allThatBeginWith(String prefix){
        if (initialized) {
            startTime = System.nanoTime();
            List<String> results = new ArrayList<>();
            for (int index = 0; index < dictionaryList.size(); index++) {
                String word = dictionaryList.get(index);
                if (word.startsWith(prefix)) {
                    results.add(word);
                }
            }
            endTime = System.nanoTime();
            return results;
        } else {
            throw new IllegalStateException("Must call initialize() prior to calling this method");
        }
    }

    @Override
    public double getLastOperationTime(){
        if (initialized) {
            return endTime - startTime;
        } else {
            throw new IllegalStateException("Must call initialize() prior to calling this method");
        }
    }

}
