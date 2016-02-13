package src;

import java.util.List;
import java.util.ArrayList;

/**
 * KTunnel is a O(k) movement of a single block through the robot with k turns
 */
public class KTunnel implements Movement {
	private int currStep = 0;
    private int k;
    private final Robot r;

    /**
     * KTunnel finds a tunnel with at most k turns through the robot.
     * Note that we may find a tunnel with fewer than k turns, however k we 
     * find must have the same parity as the given k (as start and end must be
     * leaf nodes)
     */
    public KTunnel(Robot r, int k, Coordinate start, Coordinate end) {
        //get Start Module

        // need to determine which direction to tunnel??
        // determine module to push in and direction
        // then determine which way to push out
        this.r = r;
    }

    public void step() {
        
        currStep++;
    }

    public void finalize() {

    }

    public boolean reachedEnd() {
        return false;
    }

    /**
     * returns an opposite movement
     */
    public Movement invert() {
        return null;
    }

    public Robot getRobot() {
        return r;
    }
}
