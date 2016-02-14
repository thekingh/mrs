
package ralgorithm;

import rgraph.*;
import rutils.*;

/**
 * Movement is a logical step of operations performable on a robot.
 * <p>
 * Current valid Movements include:
 * <ul>
 * <li> Slide </li>
 * <li> kTunnel </li>
 * <li> OneTunnel </li>
 * 
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public interface Movement {

    /**
     * Run a single step of the movement.
     * <p>
     * Number of steps is tracked in implementation
     */
    public void step();

    /**
     * Determines is the Movement has completed all steps
     * 
     * @return True if done
     */
    public boolean reachedEnd();

    /**
     * Inverts a movement by doing to opposite (i.e. Slide in reverse Direction)
     *
     * @return a new Movement that is this inverted
     */
    public Movement invert();

    /**
     * Runs final cleanup to update robot, switch units in modules etc.
     */
    public void finalize();

    /**
     * @return the robot associated with the movement
     */
    public Robot getRobot();

}
