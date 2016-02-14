
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

import java.net.URL;
import java.net.URLClassLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;

public class Robot {
	private ModuleGraph moduleGraph;
	private UnitGraph unitGraph;

	private boolean isModuleGridCurrent;
	private boolean isUnitGridCurrent;

    private int stateCount;

    public Robot(boolean[][] moduleBools, boolean expanded) {
        this.moduleGraph = ModuleGraph.initFromBools(moduleBools, expanded);
        this.unitGraph = this.moduleGraph.generateUnitGraph();

        stateCount = 0;
    }

	public Robot(ModuleGraph moduleGraph) {
		this.moduleGraph = moduleGraph;
        this.unitGraph = this.moduleGraph.generateUnitGraph();

        stateCount = 0;
	}


	public Graph getUnitGraph() {
		return unitGraph;
	}
    public Graph getModuleGraph() {
        return moduleGraph;
    }


    /**
     * In the robot operations silently fail, the graph throws errors
     */
    public void extend(Unit u, int dir) {
        if (u != null) {
            u.extend(dir);
        }
    }
    public void extend(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            u1.extend(u1.findNeighborDirection(u2));
        }
    }

    public void contract(Unit u, int dir) {
        if (u != null) {
            u.contract(dir);
        }
    }
    public void contract(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            u1.contract(u1.findNeighborDirection(u2));
        }
    }
    

    public void connect(Unit u1, Unit u2, int dir) {
        if (u1 != null && u2 != null) {
            unitGraph.addEdge(u1, u2, dir);
        }
    }
    public void connect(Unit u1, Unit u2, int dir, boolean isExtended) {
        if (u1 != null && u2 != null) {
            unitGraph.addEdge(u1, u2, dir, isExtended);
        }
    }
    public void connect(Module m1, Module m2, int dir) {
        if (m1 != null && m2 != null) {
            moduleGraph.addEdge(m1, m2, dir);
        }
    }
    public void connect(Module m1, Module m2, int dir, boolean isExtended) {
        if (m1 != null && m2 != null) {
            moduleGraph.addEdge(m1, m2, dir, isExtended);
        }
    }


    public void disconnect(Unit u1, Unit u2) {
        if (u1 != null && u2 != null) {
            unitGraph.removeEdge(u1, u2);
        }
    }
    public void disconnect(Unit u, int dir) {
        if (u != null) {
            unitGraph.removeEdge(u, dir);
        }
    }
    public void disconnect(Module m1, Module m2) {
        if (m1 != null && m2 != null) {
            moduleGraph.removeEdge(m1, m2);
        }
    }
    public void disconnect(Module m, int dir) {
        if (m != null) {
            moduleGraph.removeEdge(m, dir);
        }
    }

    public Module[][] toModuleArray() {
        return moduleGraph.toModuleArray();
    }

    public Unit[][] toUnitArray() {
        return unitGraph.toUnitArray();
    }

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

    public void drawModule() {
        System.out.println(moduleGraph.toGrid());
    }

    //TODO make this work
    public State getState() {
        return null;
    }

    /**
     * returns true if the robot is connected (through units).
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
     * Two robots are equal if:
     *      All modules in moduleArray are in same location
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
                } else  if (ms1[i][j] == null && ms2[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }


}
