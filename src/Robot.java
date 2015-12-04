
package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Robot {
	private Graph moduleGraph;
	private Graph unitGraph;
	private Grid moduleGrid;
	private Grid unitGrid;

	private boolean isModuleGridCurrent;
	private boolean isUnitGridCurrent;


	public Robot(Graph moduleGraph) {
		// what to use?
		this.moduleGraph = moduleGraph;
	}

	public Graph getUnitGraph() {
		generateUnitGraph();
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

			uNodes.addAll(m.getUnitMap());
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


    /*
    public Module[] getSlideNeighbors(Module M, int dir, int neighborDir) {
        Module M2 = M.getNeighbor(neighborDir);
        Module M3 = M2.getNeighbor(dir);
        Module[] toReturn = {M2, M3};
        return toReturn;
    }
    */

    public int opposite(int dir) {
        return (dir + 2) % 4;
    }

    /**
     * Performs slide operations.
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
    private void performSlide(Module M, int dir, int neighborDir, int step) {
        Module M2 = (Module) M.getNeighbor(neighborDir);
        Module M3 = (Module) M2.getNeighbor(dir);
        Unit u1 = M2.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u2 = M2.getUnitInQuadrant(opposite(neighborDir), dir);
        Unit u3 = M3.getUnitInQuadrant(opposite(neighborDir), opposite(dir));
        Unit u4 = M3.getUnitInQuadrant(opposite(neighborDir), dir);
        performHalfSlide(M, u1, u2, u3, dir, neighborDir, step);
        performHalfSlide(M, u2, u3, u4, dir, neighborDir, step);

        /*
        List <Unit> mSide = M.getSideUnits(neighborDir);
        Unit trailing;
        Unit leading;
        if (dir == 0 || dir == 3) {
            trailing = mSide.get(1);
            leading = mSide.get(0);
        } else {
            trailing = mSide.get(0);
            leading = mSide.get(1);
        }
        switch(step) {
            //This seems bad... TODO
            case 0:
            case 3:
                //Disconnect leading, connect trailing, disconnect top trailing
                trailing.connect(neighborDir);
                leading.disconnect(neighborDir);
                //TODO disconnect top trailing
                //
                //expand interior
                break;
            case 1:
            case 4:
                M.expandInteriorEdges(dir % 2 == 0); //true if vertical 0, 2
                break;
            case 2:
            case 5:
                trailing.disconnect(neighborDir);
                leading.connect(neighborDir);
                break;
            default:
                break;
        }
        */
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



        

        return true;
    }



}
