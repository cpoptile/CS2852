/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class Dot
 * Name: poptilec
 * Created 3/20/2020
 */

/**
 * Dot class represents a dot to be drawn on the canvas as described
 * in given .dot file
 */
public class Dot {

    private double x;
    private double y;

    /** Constructor creates a Dot object with given
     * center coordinates
     * @param x x-coordinate of dot center
     * @param y y-coordinate of dot center
     */
    Dot(double x, double y){
        this.x = x;
        this.y = y;
    }

    /** Method returns the x coordinate of the Dot object
     * @return x coordinate of Dot object
     */
    double getX(){
        return x;
    }

    /** Method returns the y coordinate of the Dot object
     * @return y coordinate of the Dot object
     */
    double getY(){
        return y;
    }

}
