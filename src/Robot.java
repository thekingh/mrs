
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

    public Graph getModuleGraph() {
        return moduleGraph;
    }

	public Graph getUnitGraph() {
		return unitGraph;
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

    //TODO this should live somewhere else
    public boolean directionIsValid(int dir) {
        return 0 <= dir && dir < 4;
    }


    /**
     * finds the neighbor direction to slide on if possible, returns -1 if
     * not possible to slide on either side.
     */
    private int getNeighborDir(Module M, int dir) {
        int leftDir = (dir + 3) % 4; //THIS IS REALLY DUMB but -1 doesnt work
        int rightDir = (dir + 1) % 4;
        if (M.hasNeighborInDirection(leftDir)) {
            Module M2 = (Module) M.getNeighbor(leftDir);
            if (M2.hasNeighborInDirection(dir)) {
                return leftDir;
            }
        }
        if (M.hasNeighborInDirection(rightDir)) {
            Module M2 = (Module) M.getNeighbor(rightDir);
            if (M2.hasNeighborInDirection(dir)) {
                return rightDir;
            }
        }
        return -1;
    }

    public void drawUnit() {
        
        delay(500);
        System.out.println(unitGraph.toGrid());
/*        exportToFile("../viz/states/state" + (stateCount++) + ".rbt");*/
/*        exportToFile("../viz/json_states/state" + (stateCount++) + ".json");*/
    }

/*    public void exportToFile(String path) {*/
/*        */
/*        try {*/
/*            FileWriter file = new FileWriter(path);*/
/*            JSONArray robots = generateJSONRobots();*/
/*            file.write(robots.toJSONString());*/
/*            file.flush();*/
/*            file.close();*/
/*        } catch (IOException e) {*/
/*            e.printStackTrace();*/
/*        }*/
/**/
/*    }*/

/*    public JSONArray generateJSONRobots() {*/
/**/
/*        JSONArray robots = new JSONArray();*/
/**/
/*        Grid grid = unitGraph.toGrid();*/
/**/
/*        List<GridObject> nodes = grid.getNodes();*/
/*        for(GridObject g : nodes) {*/
/**/
/*            JSONObject rbt = new JSONObject();*/
/**/
/*            Coordinate c = g.c();*/
/**/
/*            rbt.put("x", c.x());*/
/*            rbt.put("y", c.y());*/
/**/
/*            Node n = (Node)g.o();*/
/*            for(int dir = 0; dir < 4; dir++) {*/
/**/
/*                Edge e = n.getEdge(dir);*/
/*                */
/*                if( e == null) {*/
/*                    rbt.put(("ext" + Integer.toString(dir)), -1);*/
/*                    rbt.put(("con" + Integer.toString(dir)), -1);*/
/*                } else {*/
/**/
/*                    if(e.isExtended()) {*/
/*                        rbt.put(("ext" + Integer.toString(dir)), 1);*/
/*                    } else {*/
/*                        rbt.put(("ext" + Integer.toString(dir)), 0);*/
/*                    }*/
/**/
/*                    if(e.isConnected()) {*/
/*                        rbt.put(("con" + Integer.toString(dir)), 1);*/
/*                    } else {*/
/*                        rbt.put(("con" + Integer.toString(dir)), 0);*/
/*                    }*/
/*                }*/
/*            } */
/**/
/*            robots.add(rbt);*/
/*            System.out.println("added a robot object");*/
/*        }*/
/*        return robots;*/
/*    }*/

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


}
