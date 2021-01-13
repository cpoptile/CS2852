/*
 * Course: CS2852
 * Spring 2020
 * File header contains interface AutoCompleter
 * Name: poptilec
 * Created 3/11/2020
 */

import java.util.List;

/**
 * Interface to be used by the four strategies that will be selected to
 * search words in a given file
 */
public interface AutoCompleter {

    /** Method loads the given dictionary file
     * @param filename dictionary file to be loaded
     */
    void initialize(String filename);

    /** Method navigates through given file to return a list of words
     * that match with given prefix
     * @param prefix prefix of word to be searched
     * @return List of words in file that match given prefix
     */
    List<String> allThatBeginWith(String prefix);

    /** Method calculates the number of nanoseconds required by the last call to
     * initialize() or allThatBeginWith()
     * @return number of nanoseconds
     */
    double getLastOperationTime();

}

/* To compare the performance of the different strategies, I loaded the same
text file (the words.txt file provided on Dr. Taylor's website) for each strategy.
I observed the time it took each strategy to load the file as well as the time it took
each strategy to provide matches to the same letter/phrase. I found that using a LinkedList
with for-each loops provided the fastest times with the LinkedList using index methods, ArrayList
using for-each loops, and ArrayList using index methods following behind in increasing time order.
UPDATE: After testing this method multiple times, I've been getting mixed results so I'm just going
to write a conclusion based on assumptions. Technically a for-each loop should have a faster
performance than a standard for loop as it is:
1. more simpler
2. does not need to establish an index/stopping point by calling list.size()
3. provides direct access to the element stored in the list
In theory, it should also be faster to search an ArrayList as its elements are stored
in an index based system whereas LinkedList stores its elements by using references
or pointers to the next one. 
 */
