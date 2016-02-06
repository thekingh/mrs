
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
     * performs half a contracted slide.
     * NOTE: This redefines Aloupis' contracted slide by only moving the bottom
     * layer of the sliding moduel
     */
    public void performContractedHalfSlide(Module m, Unit u1, Unit u2, Unit u3,
                                 int dir, int neighborDir, int step) {
        List <Unit> mSide = m.getSideUnits(neighborDir);
        Unit trailing = m.getUnitInQuadrant(neighborDir, opposite(dir));
        Unit leading  = m.getUnitInQuadrant(neighborDir, dir);
        Edge leadingEdge;
        Edge trailingEdge;
        
        //TODO REMOVE
        step = step % 5;
    
        switch(step) {
            case 0:
                leadingEdge = leading.getEdge(neighborDir);
                unitGraph.removeEdge(leadingEdge);
                unitGraph.addEdge(trailing, u1, neighborDir);
                leading.disconnect(opposite(neighborDir));
                break;
            case 1:
                trailing.extend(dir);
                break;
            case 2:
                trailingEdge = trailing.getEdge(neighborDir);
                unitGraph.addEdge(leading, u3, neighborDir);
                unitGraph.removeEdge(trailingEdge);
                break;
            case 3:
                trailing.contract(dir);
                break;
            case 4:
                unitGraph.addEdge(trailing, u2, neighborDir);
                leading.connect(opposite(neighborDir));
                break;
            default:
                System.out.println("OMG");
                break;
        }
    }
    public void performExpandedHalfSlide(Module m, Unit u1, Unit u2, Unit u3,
                                 int dir, int neighborDir, int step) {
        List <Unit> mSide = m.getSideUnits(neighborDir);
        Unit trailing = m.getUnitInQuadrant(neighborDir, opposite(dir));
        Unit leading  = m.getUnitInQuadrant(neighborDir, dir);
        Edge leadingEdge;
        Edge trailingEdge;
        //TODO REMOVE
        step = step % 5;
        switch(step) {
            case 0:
                unitGraph.removeEdge(leading.getEdge(neighborDir));
                unitGraph.addEdge(trailing, u1, neighborDir, true);
                u1.disconnect(neighborDir);
                u2.disconnect(neighborDir);
                u1.disconnect(opposite(dir));

                break;
            case 1:
                u1.contract(dir);
                u2.contract(dir);
                break;
            case 2:
                trailingEdge = trailing.getEdge(neighborDir);
                unitGraph.addEdge(leading, u3, neighborDir, true);
                unitGraph.removeEdge(trailingEdge);
                break;
            case 3:
                u1.extend(dir);
                u2.extend(dir);
                break;
            case 4:
                unitGraph.addEdge(trailing, u2, neighborDir, true);
                u1.connect(neighborDir);
                u2.connect(neighborDir);
                u1.connect(opposite(dir));
                break;
            default:
                System.out.println("OMG");
                break;
        }
    }

    public void performHalfSlide(Module m, Unit u1, Unit u2, Unit u3, int dir,
                                 int neighborDir, int step, boolean expanded) {
        if (expanded) {
            performExpandedHalfSlide(m, u1, u2, u3, dir, neighborDir, step);
        } else {
            performContractedHalfSlide(m, u1, u2, u3, dir, neighborDir, step);
        }
    }

    /**
     * Performs slide operations in 10 steps.
     * Assumptions made:
     * 1) all modules are in a contracted state between move phases
     * 2) module size is 2
     * 3) dir and neighbor are adjacent directions
     *
     * 1) must have edges between them (not necessarily connected)
     *
     * Slides can be made in parallel on the same module
     *
     * @param M             Module to slide
     * @param dir           Direction to slide module
     * @param neighborDir   Direction of neighboring modules to slide against
     */
    private void performSlide(Module m, int dir, int neighborDir, boolean expanded) {
        Module m2 = (Module) m.getNeighbor(neighborDir);
        Module m3 = (Module) m2.getNeighbor(dir);
        print(String.format("neighbordir %d", neighborDir));
        print (String.format("Dir is %d", dir));
        Unit u1 = m2.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u2 = m2.getUnitInQuadrant(opposite(neighborDir), dir);
        Unit u3 = m3.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u4 = m3.getUnitInQuadrant(opposite(neighborDir), dir);
        print(String.format("u1 %d", u1.getId()));
        print(String.format("u2 %d", u2.getId()));
        print(String.format("u3 %d", u3.getId()));
        print(String.format("u4 %d", u4.getId()));
        for (int step = 0; step < 10; step++) {
            if (step < 5) {
                performHalfSlide(m, u1, u2, u3, dir, neighborDir, step, expanded);
            } else{ 
                performHalfSlide(m, u2, u3, u4, dir, neighborDir, step, expanded);
            }
            drawUnit();
        }

        //updating module graph
        moduleGraph.removeEdge(m.getEdge(neighborDir));
        moduleGraph.addEdge(m, m3, neighborDir);
        drawModule();
    }

    /**
     * Performs a unit slide if possible on a module in a given direction.
     *
     * @param M         Module to slide, note type Module not Node
     * @param dir       direction to slide relative to rest of robot
     *
     * @return          Slide status, returns true if performed successfully
     */
    public boolean slide(Module m, int dir, boolean expanded) {
        int neighborDir = getNeighborDir(m, dir);
        return slide(m, dir, neighborDir, expanded);
    }

    public boolean slide(Module m, int dir, int neighborDir, boolean expanded) {
        if (neighborDir == -1) {
            System.out.println("ERROR SLIDE NOT POSSIBLE");
            return false;
        }
        performSlide(m, dir, neighborDir, expanded);
        return true;
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

    public int opposite(int dir) {
        return (dir + 2) % 4;
    }

    public void drawUnit() {
        
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls) {
            System.out.println(url.getFile());
        }

        delay(500);
        System.out.println(unitGraph.toGrid());
/*        exportToFile("../viz/states/state" + (stateCount++) + ".rbt");*/
        exportToFile("../viz/json_states/state" + (stateCount++) + ".json");
        System.out.println("XXXXXXXXXXXXXXXXXXX");
        System.out.println("sc: " + stateCount);
    }

    public void exportToFile(String path) {
        
        try {
            FileWriter file = new FileWriter(path);
            JSONArray robots = generateJSONRobots();
            file.write(robots.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JSONArray generateJSONRobots() {

        JSONArray robots = new JSONArray();

        Grid grid = unitGraph.toGrid();

        List<GridObject> nodes = grid.getNodes();
        for(GridObject g : nodes) {

            JSONObject rbt = new JSONObject();

            Coordinate c = g.c();

            rbt.put("x", c.x());
            rbt.put("y", c.y());

            Node n = (Node)g.o();
            for(int dir = 0; dir < 4; dir++) {

                Edge e = n.getEdge(dir);
                
                if( e == null) {
                    rbt.put(("ext" + Integer.toString(dir)), -1);
                    rbt.put(("con" + Integer.toString(dir)), -1);
                } else {

                    if(e.isExtended()) {
                        rbt.put(("ext" + Integer.toString(dir)), 1);
                    } else {
                        rbt.put(("ext" + Integer.toString(dir)), 0);
                    }

                    if(e.isConnected()) {
                        rbt.put(("con" + Integer.toString(dir)), 1);
                    } else {
                        rbt.put(("con" + Integer.toString(dir)), 0);
                    }
                }
            } 

            robots.add(rbt);
            System.out.println("added a robot object");
        }
        return robots;
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


}
