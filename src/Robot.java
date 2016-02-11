
package src;

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
	private Graph moduleGraph;
	private Graph unitGraph;

	private boolean isModuleGridCurrent;
	private boolean isUnitGridCurrent;

    private int stateCount;

    public Robot(boolean[][] moduleBools, boolean expanded) {
        //TODO check me
        int w = moduleBools.length;
        int h = moduleBools[0].length;

        Module[][] modules = new Module[w][h];
        Set<Node> moduleSet = new HashSet<Node>();
        Set<Edge> edgeSet = new HashSet<Edge>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (moduleBools[i][j]) {
                    modules[i][j] = new Module(expanded);
                    moduleSet.add(modules[i][j]);
                }
            }
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (moduleBools[i][j]) {
                    for (int dir = 0; dir < 4; dir++) {
                        Coordinate c = new Coordinate(i, j);
                        Coordinate r = c.calcRelativeLoc(dir);
                        if (r.inBounds(w, h) && moduleBools[r.x()][r.y()]) {
                            Edge e = modules[i][j].addNeighbor(modules[r.x()][r.y()], dir, expanded, true);
                            edgeSet.add(e);
                        }
                    }
                }
            }
        }

        Graph g = new Graph(moduleSet, edgeSet);
        this.moduleGraph = g;
        generateUnitGraph();

        stateCount = 0;
    }

	public Robot(Graph moduleGraph) {
		// what to use?
		this.moduleGraph = moduleGraph;
        generateUnitGraph();

        stateCount = 0;
	}


	public Graph getUnitGraph() {
		return unitGraph;
	}
    public Graph getModuleGraph() {
        return moduleGraph;
    }

    public void extend(Unit u, int dir) {
        u.extend(dir);
    }

    public void contract(Unit u, int dir) {
        u.contract(dir);
    }

    public void connect(Unit u1, Unit u2, int dir) {
        unitGraph.addEdge(u1, u2, dir);
    }
    public void connect(Module m1, Module m2, int dir) {
        moduleGraph.addEdge(m1, m2, dir);
    }

    public void disconnect(Unit u1, Unit u2) {
        unitGraph.removeEdge(u1, u2);
    }
    public void disconnect(Module m1, Module m2) {
        moduleGraph.removeEdge(m1, m2);
    }

    public Module[][] toModuleArray() {
        Object[][] grid = moduleGraph.toGrid(true).getGrid();
        int w = grid.length;
        int h = grid[0].length;

        Module[][] modules = new Module[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                modules[i][j] = (Module) grid[i][j];
            }
        }

        return modules;
    }

	// generates unit graph from module graph
	// NEEDSWORK: want to return unit graph? record status?
	private void generateUnitGraph() {
		Set<Node> uNodes = new HashSet<Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Set<Node> mNodes = moduleGraph.getNodes();
		Set<Edge> mEdges = moduleGraph.getEdges();

		Module m1, m2;

        // NEEDSWORK: could do with only nodes and getExteriorSubEdges in all direc
		for (Edge mEdge : mEdges) {
			m1 = (Module) mEdge.getN1();
			m2 = (Module) mEdge.getN2();

			uEdges.addAll(m1.getExteriorSubEdges(m2));
		}

		Module m;
		for (Node n : mNodes) {
			m = (Module) n;
			uEdges.addAll(m.getInteriorEdges());
			uNodes.addAll(m.getUnitSet());
		}

		unitGraph = new Graph(uNodes, uEdges);
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


    //TODO remove me
    private void print(Object o) {
        System.out.println(o);
    }

    //TODO make this work
    public State getState() {
        return null;
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
