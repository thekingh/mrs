
package src;

//TODO getters

/**
 * Movement is a logical step of operations performable on a robot.
 * Current valid movements include: slide
 * 
 */
public interface Movement {

    /**
     * run a single step of the movement.
     * Number of steps is tracked in implementation
     */
    public void step();

    /**
     * returns true when movement has completed
     */
    public boolean reachedEnd();

    /**
     * returns an opposite movement
     */
    public Movement invert();

    /**
     * runs final cleanup to update robot, switch units in modules etc.
     */
    public void finalize();

    /**
     * returns the robot associated with the movement
     */
    public Robot getRobot();

}
