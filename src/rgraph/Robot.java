package rgraph;

import rutils.*;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Robot represents a connect set of units.
 * <p>
 * A robot has two representations to speed up algorithmic running time. It has
 * both a graphical and a spatial representation. The natural representation is
 * a graphical relation between logical modules in the graph. From this
 * representation we can create a spatial representation, an k-d representation
 * of the robot which reflect actual module locations in space.
 *
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 */
public class Robot {
	private ModuleGraph moduleGraph;
	private UnitGraph   unitGraph;
    private int         stateCount;

    /**
     * Constructs a robot from a 2d array of modules.
     * @param moduleBools 2d boolean array specifying locations of modules.
     * @param expanded    false implies 2x2 modules taking up 2x2 space,
     *                    true implies 2x2 modules taking up 3x3 space.
     */
    public Robot(boolean[][] moduleBools, boolean expanded) {
        this(ModuleGraph.initFromBools(moduleBools, expanded));
    }

    /**
     * Constructs a robot from a module graph.
     * <p>
     * This allows construction of a new robot from a subset of another robot,
     * i.e. allowing for splits or combinations of robots.
     * @param moduleGraph graph representing the modules of the robot to construct
     */
	public Robot(ModuleGraph moduleGraph) {
		this.moduleGraph = moduleGraph;
        this.unitGraph = this.moduleGraph.generateUnitGraph();

        stateCount = 0;
	}


    /**
     * Returns unitGraph representation of robot
     * @return unitGraph graph of robot
     */
	public Graph getUnitGraph() {
		return unitGraph;
	}

    /**
     * Returns moduleGraph representation of robot
     * @return moduleGraph graph of robot
     */
    public Graph getModuleGraph() {
        return moduleGraph;
    }

    /**
     * In the robot operations silently fail, the graph throws errors.
     */

    /**
     * Extends an edge relative to a unit.
     * @param u     Unit of interest
     * @param dir   Direction of arm relative to unit u
     */
    public void extend(Unit u, int dir) {
        if (u != null) {
            u.extend(dir);
        }
    }

    /**
     * Extends an edge between two units.
     * @param u1    First unit of interest
     * @param u2    Second unit of interest
     */
    public void extend(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            u1.extend(u1.findNeighborDirection(u2));
        }
    }

    /**
     * Contracts an edge relative to a unit.
     * @param u     Unit of interest
     * @param dir   Direction of arm relative to unit u
     */
    public void contract(Unit u, int dir) {
        if (u != null) {
            u.contract(dir);
        }
    }

    /**
     * Contracts an edge between two units.
     * @param u1    First unit of interest
     * @param u2    Second unit of interest
     */
    public void contract(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            u1.contract(u1.findNeighborDirection(u2));
        }
    }

    /**
     * Connects two units with a contracted edge in direction from u1 to u2.
     * @param u1    First unit of interest
     * @param u2    Second unit of interest
     * @param dir   Direction from u1 to u2
     */
    public void connect(Unit u1, Unit u2, int dir) {
        if (u1 != null && u2 != null) {
            unitGraph.addEdge(u1, u2, dir);
        }
    }

    /**
     * Connects two units with an edge in direction from u1 to u2.
     * @param u1    First unit of interest
     * @param u2    Second unit of interest
     * @param dir   Direction from u1 to u2
     * @param isExtended    true constructs an extended edge
     */
    public void connect(Unit u1, Unit u2, int dir, boolean isExtended) {
        if (u1 != null && u2 != null) {
            unitGraph.addEdge(u1, u2, dir, isExtended);
        }
    }

    /**
     * Connects two modules with a contracted edge in direction from m1 to m2.
     * @param m1    First module of interest
     * @param m2    Second module of interest
     * @param dir   Direction from m1 to m2
     */
    public void connect(Module m1, Module m2, int dir) {
        if (m1 != null && m2 != null) {
            moduleGraph.addEdge(m1, m2, dir);
        }
    }

    /**
     * Connects two modules with an edge in direction from m1 to m2.
     * @param m1    First module of interest
     * @param m2    Second module of interest
     * @param dir   Direction from m1 to m2
     * @param isExtended    true constructs an extended edge
     */
    public void connect(Module m1, Module m2, int dir, boolean isExtended) {
        if (m1 != null && m2 != null) {
            moduleGraph.addEdge(m1, m2, dir, isExtended);
        }
    }

    /**
     * Disconnects the edge between two units.
     * @param u1    First unit of interest
     * @param u2    Second unit of interest
     */
    public void disconnect(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            unitGraph.removeEdge(u1, u2);
        }
    }

    /**
     * Disconnects an edge relative to a unit.
     * @param u     Unit of interest
     * @param dir   Direction of arm relative to unit u
     */
    public void disconnect(Unit u, int dir) {
        if (u != null) {
            unitGraph.removeEdge(u, dir);
        }
    }

    /**
     * Disconnects the edge between modules.
     * @param m1    First module of interest
     * @param m2    Second module of interest
     */
    public void disconnect(Module m1, Module m2) {
        if (m1 != null && m2 != null) {
            moduleGraph.removeEdge(m1, m2);
        }
    }

    /**
     * Disconnects an edge relative to a module.
     * @param m     Module of interest
     * @param dir   Direction of arm relative to module m
     */
    public void disconnect(Module m, int dir) {
        if (m != null) {
            moduleGraph.removeEdge(m, dir);
        }
    }

    /**
     * Gets robot representation as 2D array of modules.
     * @return 2D array of modules
     */
    public Module[][] toModuleArray() {
        return moduleGraph.toModuleArray();
    }

    /**
     * Gets robot representation as 2D array of units.
     * @return 2D array of units
     */
    public Unit[][] toUnitArray() {
        return unitGraph.toUnitArray();
    }

    /**
     * DEBUG method, draws module graph representation on console.
     */
    public void drawModule() {
        System.out.println(moduleGraph.toGrid());
    }

    /**
     * DEBUG method, draws unit graph representation on console.
     */
    public void drawUnit() {
        delay(500);
        System.out.println(unitGraph.toGrid());
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis); 
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets new state object snapshot from robot
     * @return State object representing robot
     */
    public State getState() {
        return new State(this);
    }

    /**
     * returns true if the robot is connected (through units).
     * @return true iff robot is connected.
     */
    public boolean isConnected() {
        int size = unitGraph.size();
        int count = 0;
        Object[][] grid = unitGraph.toGrid(true).getGrid();
        int w = grid.length;
        int h = grid[0].length;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (grid[i][j] != null) {
                    count++;
                }
            }
        }
        return count == size;
    }

    /**
     * Tests Robot Equality.
     * <p>
     * Two robots are equal if:
     * <ul>
     *      <li>All modules in moduleArray are in same location</li>
     * </ul>
     * @param other     Robot to test against
     * @return          true if robots "look" the same, i.e. robots have
     *                  modules in the same locations.
     */
    public boolean equals(Robot other) {
        Module[][] ms1 = this.toModuleArray();
        Module[][] ms2 = other.toModuleArray();

        int w = ms1.length;
        int h = ms1[0].length;

        //check dims
        if (w != ms2.length || h != ms2[0].length) {
            return false;
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (ms1[i][j] != null && ms2[i][j] == null) {
                    return false;
                } else if (ms1[i][j] == null && ms2[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}