package rgraph;

import rutils.*;


/**
 * Unit represents the atomic element of a module robot.
 * <p>
 * represents in 2d a unit square that has 4 controllable arms one extending
 * from the center of each of its edges. Each arm has the following functionality
 * <ol>
 *  <li> Extend 0.5 unit lengths </li>
 *  <li> Connect to a neighboring units arm </li>
 * </ol>
 * In our model a unit is not limited by physics and therefore can lift all 
 * n units of a module robot.
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 */
public class Unit extends Node {

    /**
     * Connects an edge in a direction if possible.
     * @param dir   direction to connect in
     */
    public void connect(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsConnected(true);
        }
    }

    /**
     * Disconnects an edge in a direction if possible.
     * @param dir   direction to disconnect in
     */
    public void disconnect(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsConnected(false);
        }
    }

    /**
     * Extends an edge in a direction if possible.
     * @param dir   direction to extend in
     */
    public void extend(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsExtended(true);
        }
    }

    /**
     * Contracts an edge in a direction if possible.
     * @param dir   direction to contract edge
     */
    public void contract(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsExtended(false);
        }
    }

    /**
     * DEBUG: String representation of a unit 
     * @return string rep of unit for debugging "o"
     */
	public String toString() {
        return "o";
	}
}
