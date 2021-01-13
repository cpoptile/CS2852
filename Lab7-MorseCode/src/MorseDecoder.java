/*
 * Course: CS2852
 * Spring 2020
 * File header contains class MoreDecoder
 * Name: poptilec
 * Created 5/1/2020
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class decodes an inputted file based off the morsecode.txt file
 */
public class MorseDecoder {

    private static MorseTree tree = new MorseTree<>();
    private static ArrayList<String> decodedFile = new ArrayList<>();

    public static void main(String[] args) {
        loadDecoder(Paths.get("morsecode.txt"));
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter an input filename: ");
        String inFile = scan.next();
        readFile(inFile);
        System.out.println("Please enter an output filename: ");
        String outFile = scan.next();
        writeFile(outFile);
    }

    /** Method decodes a file containing morse code into its alphabetical counterparts
     * @param filename filename to be decoded
     */
    public static void readFile(String filename){
        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] codeSnips = line.split("\\s+");
                for (String code : codeSnips) {
                    if (tree.decode(code) == null) {
                        System.out.println("Warning: skipping " + code);
                    } else {
                        String letter = (String) tree.decode(code);
                        decodedFile.add(letter);
                    }
                }
            }
        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("File was not found or is null");
        }
    }

    /** Method takes decoded contents of inputte file to then write
     * the decoded message to a file given by user
     * @param filename filename of where the decoded message will be written to
     */
    public static void writeFile(String filename){
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file.getPath())) {
            for (String code: decodedFile){
                if (code.equals("\\n")){
                    writer.write("\n");
                } else {
                    writer.write(code);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }

    }

    /** Method populates the MorseTree using contents of the
     * morsecode.txt file
     * @param path path of morsecode.txt file
     */
    public static void loadDecoder(Path path){
        try (Scanner scan = new Scanner(path.toFile())) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.charAt(0) == '\\') {
                    String symbol = line.substring(0, 2);
                    String codeword = line.substring(2);
                    tree.add(symbol, codeword);
                } else {
                    String symbol = line.substring(0, 1);
                    String codeword = line.substring(1);
                    tree.add(symbol, codeword);
                }
            }
        } catch (NullPointerException | FileNotFoundException e){
            System.out.println("File was not found");
        }

    }
}
