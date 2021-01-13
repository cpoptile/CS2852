/*
 * Course: CS1021-091
 * Winter 2019
 * File header contains class Dot
 * Name: poptilec
 * Created 3/31/2020
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

    /** Method calculates the critical value of a dot based on its neighboring dots
     * @param previous previous dot
     * @param next next dot
     * @return critical value
     */
    double calculateCriticalValue(Dot previous, Dot next){
        double changeInXd12 = Math.pow((previous.getX()-x), 2.0);
        double changeInYd12 = Math.pow((previous.getY()-y), 2.0);
        double d12 = Math.sqrt(changeInXd12+changeInYd12);
        double changeInXd23 = Math.pow((x-next.getX()), 2.0);
        double changeInYd23 = Math.pow((y-next.getY()), 2.0);
        double d23 = Math.sqrt(changeInXd23+changeInYd23);
        double changeInXd13 = Math.pow((previous.getX()-next.getX()), 2.0);
        double changeInYd13 = Math.pow((previous.getY()-next.getY()), 2.0);
        double d13 = Math.sqrt(changeInXd13+changeInYd13);
        return d12+d23-d13;
    }

}
