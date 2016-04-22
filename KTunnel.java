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
 * @since 4/11/2016
 */
public class KTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 37;

    private final Robot r;
    private final int k;
    private final List<Coordinate> turns;

    public KTunnel(Robot r, List<Coordinate> turns) {
        // need to determine which direction to tunnel??
        // determine module to push in and direction
        // then determine which way to push out
        this.r     = r;
        this.k     = turns.size();
        this.turns = turns;
    }

    /**
     * Instantiates a kTunnel from start to end
     * <p>
     * KTunnel finds a tunnel with at most k turns through the robot.
     * Note: we may find a tunnel with fewer than k turns, however k we 
     * find must have the same parity as the given k
     *
     * @param r the Robot on which the kTunnel will be performed
     * @param k the number of turns in the tunnel
     * @param start the stating position of the module aka the Module to tunnel
     * @param end where the module will be tunneled to
     * @return new KTunnel object
     */
    public static KTunnel kTunnelFromStartAndEnd(Robot r, int k, Coordinate start, Coordinate end) {
        List <Coordinate> turns = findTurnCoordinates(r, k, start, end);
        return new KTunnel(r, turns);
    }

    /**
     * finds a list of k turns from one coordinate to the other through the robot graph.
     *
     * <p>
     * This is used to find a k tunnel through the graph. 
     *
     */
    private List<Coordinate> findTurnCoordinates(Robot r, int k, Coordinate start, Coordinate end) {


    }

    public void step() {
        
        currStep++;
    }

    public void finalizeMove() {

    }

    public boolean reachedEnd() {
        return false;
    }

    /**
     * returns an opposite movement
     */
    public Movement invert(Robot s) {
        return null;
    }

    public Robot getRobot() {
        return r;
    }
}
