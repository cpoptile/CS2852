/*
 * Course: CS2852
 * Spring 2020
 * File header contains class MorseTree
 * Name: poptilec
 * Created 5/1/2020
 */

/** Class is used to store morse code along with its alphabetical
 * equivalents into a binary tree
 * @param <E> generic
 */
public class MorseTree<E> {

    private Node<E> root;

    /** Subclass of Nodes to be stored and utilized in
     * binary tree
     * @param <E> generic
     */
    private static final class Node<E> {

        private Node<E> left;
        private Node<E> right;
        private E value;

        private Node(Node<E> left, Node<E> right, E value){
            this.left = left;
            this.right = right;
            this.value = value;
        }

        private Node(E value){
            this(null, null, value);
        }
    }

    /**
     * Constructor method to create the binary tree
     */
    public MorseTree(){
        root = new Node<>(null);
    }

    /** Method adds a symbol and code pairing into the binary tree
     * @param symbol symbol which represents a morse code
     * @param code morse code that represents a symbol
     */
    public void add(E symbol, String code){
        Node<E> current = root;
        for (int i = 0; i < code.length(); i++){
            if (code.substring(i, i+1).equals(".")){
                if (current.left == null){
                    current.left = new Node<>(null);
                }
                current = current.left;
            } else if (code.substring(i, i+1).equals("-")){
                if (current.right == null){
                    current.right = new Node<>(null);
                }
                current = current.right;
            }
        }
        current.value = symbol;
    }

    /** Method decodes morse code into its respective symbol
     * @param code code to be decoded
     * @return the symbol corresponding to inputted code
     */
    public E decode(String code){
        Node<E> subroot = root;
        boolean exists = false;

        for (int i = 0; i < code.length(); i++) {
            char character = code.charAt(i);
            if (character == '-') {
                subroot = subroot.right;
                exists = true;
            } else if (character == '.') {
                subroot = subroot.left;
                exists = true;
            }
        }
        return exists ? subroot.value : null;
    }
}
