
package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.PrintWriter;
import java.io.IOException;

public class Robot {
	private Graph moduleGraph;
	private Graph unitGraph;

	private boolean isModuleGridCurrent;
	private boolean isUnitGridCurrent;


	public Robot(Graph moduleGraph) {
		// what to use?
		this.moduleGraph = moduleGraph;
        generateUnitGraph();
	}

    public Graph getModuleGraph() {
        return moduleGraph;
    }

	public Graph getUnitGraph() {
		return unitGraph;
	}

	// generates unit graph from module graph
	// NEEDSWORK: want to return unit graph? record status?
	private void generateUnitGraph() {
		Set<Node> uNodes = new HashSet<Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Set<Node> mNodes = moduleGraph.getNodes();
		Set<Edge> mEdges = moduleGraph.getEdges();

		Module m1, m2;

		for (Edge mEdge : mEdges) {
			m1 = (Module) mEdge.getN1();
			m2 = (Module) mEdge.getN2();

			// NEEDSWORK: adding all the exterior edges here seems weird???
			// maybe add when we connect modules?
			uEdges.addAll(m1.addExteriorSubEdges(m2));
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


    public void performHalfSlide(Module M, Unit u1, Unit u2, Unit u3,
                                 int dir, int neighborDir, int step) {
        List <Unit> mSide = M.getSideUnits(neighborDir);
        Unit trailing = M.getUnitInQuadrant(neighborDir, opposite(dir));
        Unit leading  = M.getUnitInQuadrant(neighborDir, dir);
        Edge leadingEdge;
        Edge trailingEdge;
        
        
        //TODO REMOVE
        step = step % 5;
    
        switch(step) {
            case 0:
                leadingEdge = leading.getEdge(neighborDir);
                unitGraph.removeEdge(leadingEdge);
                unitGraph.addEdge(trailing, u1, neighborDir);
                break;
            case 1:
                M.expandInteriorEdges(dir % 2 == 0); //true if vertical 0, 2
                break;
            case 2:
                trailingEdge = trailing.getEdge(neighborDir);
                unitGraph.addEdge(leading, u3, neighborDir);
                unitGraph.removeEdge(trailingEdge);
                break;
            case 3:
                M.contractInteriorEdges(dir % 2 == 0); //true if vertical 0, 2
                break;
            case 4:
                unitGraph.addEdge(trailing, u2, neighborDir);
                break;
            default:
                System.out.println("OMG");
                break;
        }
    }

    public int opposite(int dir) {
        return (dir + 2) % 4;
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
     * @param M             Module to slide
     * @param dir           Direction to slide module
     * @param neighborDir   Direction of neighboring modules to slide against
     */
    private void performSlide(Module M, int dir, int neighborDir) {
        Module M2 = (Module) M.getNeighbor(neighborDir);
        Module M3 = (Module) M2.getNeighbor(dir);
        print(String.format("neighbordir %d", neighborDir));
        print (String.format("Dir is %d", dir));
        Unit u1 = M2.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u2 = M2.getUnitInQuadrant(opposite(neighborDir), dir);
        Unit u3 = M3.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u4 = M3.getUnitInQuadrant(opposite(neighborDir), dir);
        print(String.format("u1 %d", u1.getId()));
        print(String.format("u2 %d", u2.getId()));
        print(String.format("u3 %d", u3.getId()));
        print(String.format("u4 %d", u4.getId()));
        for (int step = 0; step < 10; step++) {
            if (step < 5) {
                performHalfSlide(M, u1, u2, u3, dir, neighborDir, step);
            } else{ 
                performHalfSlide(M, u2, u3, u4, dir, neighborDir, step);
            }
            drawUnit();
        }

        //updating module graph
        moduleGraph.removeEdge(M.getEdge(neighborDir));
        moduleGraph.addEdge(M, M3, neighborDir);
        drawModule();
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

    //TODO remove me
    private void print(Object o) {
        System.out.println(o);
    }
    /**
     * Performs a unit slide if possible on a module in a given direction
     *
     * @param M         Module to slide, note type Module not Node
     * @param dir       direction to slide relative to rest of robot
     *
     * @return          Slide status, returns true if performed successfully
     */
    public boolean slide(Module M, int dir) {
        int neighborDir = getNeighborDir(M, dir);

        if (neighborDir == -1) {
            System.out.println("ERROR SLIDE NOT POSSIBLE");
            return false;
        }
        performSlide(M, dir, neighborDir);
        return true;
    }

    public void drawUnit() {
        System.out.println(unitGraph.toGrid());
    }

    public void drawModule() {
        System.out.println(moduleGraph.toGrid());
    }

    public String getRobotString() {

        String robot = "x, y, ext0, con0, ext1, con1, ext2, con2, ext3, con3\n";

        Grid grid = unitGraph.toGrid();
        
        List<GridObject> nodes = grid.getNodes();
        for(GridObject g : nodes) {
            
            //x, y
            Coordinate c = g.c();
            robot += Integer.toString(c.x()) + "," + Integer.toString(c.y());

            Node n = (Node)g.o();
            for(int dir = 0; dir < 4; dir++ ) {
                Edge e = n.getEdge(dir);
                
                if( e == null) {
                    robot += ",-1,-1";
                } else {
                    if(e.isExtended()) {
                        robot += ",1";
                    } else {
                        robot += ",0";
                    }

                    if(e.isConnected()) {
                        robot += ",1";
                    } else {
                        robot += ",0";
                    }
                }
            }

            robot += "\n";
        }

        return robot;

    }

    public void exportToFile(String file) {
        
        try {
            PrintWriter writer = new PrintWriter(file);
            String robot = getRobotString();
            writer.println(robot);
            writer.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }


}
