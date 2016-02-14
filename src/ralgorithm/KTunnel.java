package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of KTunnel, which tunnels block trhoguh robot using 90 degree turns
 * <p>
 * KTunnel is a O(k) movement that moves a block from a start position to an
 * end position by pushing it through the robot
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class KTunnel implements Movement {
	private int currStep = 0;
    private int k;
    private final Robot r;

    /**
     * Instantiates a kTunnel from start to end
     * <p>
     * KTunnel finds a tunnel with at most k turns through the robot.
     * Note: we may find a tunnel with fewer than k turns, however k we 
     * find must have the same parity as the given k (as start and end must be
     * leaf nodes)
     *
     * @param r the Robot on which the kTunnel will be performed
     * @param k the number of turns in the tunnel
     * @param start the stating position of the module aka the Module to tunnel
     * @param end where the module will be tunneled to
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
