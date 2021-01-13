/*
 * Course: CS2852
 * Spring 2020
 * File header contains class MorseEncoder
 * Name: poptilec
 * Created 5/09/2020
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver class to select a file to be encoded into morse code
 */
public class MorseEncoder {

    private static LookupTable lookupTable = new LookupTable();
    private static final String MORSE_DICTIONARY = "morsecode.txt";
    private static ArrayList<String> encoded = new ArrayList<>();


    public static void main(String[] args) {
        addMorseCode();
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter an input filename: ");
        String inputFile = scan.nextLine();
        readFile(inputFile);
        System.out.println("Please enter an output filename: ");
        String outputFile = scan.nextLine();
        writeFile(outputFile);
    }

    /** Method loads in a file containing a message to be
     * encoded
     * @param filename filename of message to be encoded
     */
    public static void readFile(String filename){
        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                char[] symbols = scan.nextLine().toUpperCase().toCharArray();
                for (char c : symbols) {
                    String code = String.valueOf(lookupTable.get(String.valueOf(c)));
                    if (!code.equals("null")) {
                        encoded.add(code);
                    } else {
                        System.out.println("Warning: skipping " + c);
                    }
                }
                encoded.add(String.valueOf(lookupTable.get("\\n")));

            }
        } catch (NullPointerException | FileNotFoundException e){
            System.out.println("Null Pointer Exception");
        }
    }

    /** Method writes the encoded message to selected file
     * @param filename filename of where the encoded text is written to
     */
    public static void writeFile(String filename){
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file.getPath())) {
            for (String code: encoded){
                if (code.equals(String.valueOf(lookupTable.get("\\n")))){
                    writer.write(code + "\n");
                } else {
                    writer.write(code + " ");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }

    }

    /**
     *  Method loads morse code as key-value pairs into the LookupTable
     */
    public static void addMorseCode(){
        try (Scanner scan = new Scanner(Paths.get(MORSE_DICTIONARY))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.charAt(0) != '\\') {
                    lookupTable.put(line.substring(0, 1), line.substring(1));
                } else {
                    lookupTable.put(line.substring(0, 2), line.substring(2));
                }
            }
        } catch (IOException e) {
            System.out.println("An IO exception has occured");
        }
    }
}
