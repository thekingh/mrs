
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
		Map<Integer, Node> uNodes = new HashMap<Integer, Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Map<Integer, Node> mNodes = moduleGraph.getNodes();
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
		for (Node n : mNodes.values()) {
			m = (Module) n;
			uEdges.addAll(m.getInteriorEdges());

			uNodes.putAll(m.getUnitMap());
		}

		unitGraph = new Graph(uNodes, uEdges);
	}

    //TODO this should live somewhere else
    public boolean directionIsValid(int dir) {
        return 0 <= dir && dir < 4;
    }

    private boolean slideIsPossible(Module M, Module N, int dir) {
        //1
        if (!directionIsValid(dir)) {
            return false;
        }
        //2 UNTESTED TODO

        //3
        int dirOfNeighbor = -1;
        int dirClockwise  = (dir + 1) % 4;
        int dirCounterClock = (dir - 1) % 4;
        if (M.isNeighborInDirection(N, dirClockwise)) {
            dirOfNeighbor = dirClockwise;
        } else if (M.isNeighborInDirection(N, dirCounterClock)) {
            dirOfNeighbor = dirCounterClock;
        } else {
            return false;
        }
        //4
        if (!N.hasNeighborInDirection(dir)) {
            return false;
        }
        return true;
    }

    /**
     * Tests if given a module if sliding in a direction is possible.
     * 
     * A slideIsPossible if the following conditions are met:
     * 1) direction is in the set {0,1,2,3}
     * 2) M in set of Nodes
     * 3) M has a neighbor N in either the dir + 1 or dir - 1 directions (mod 4)
     *      with property 4
     * 4) N has a neighbor in dir direction
     * 5) Assert that nothing is inside the module
     *
     * Unchecked conditions:
     * 1) if M has other neighbors besides N, then MN may not be on a cycle of G
     * 2) The space relative to M in the direction dir is empty of Nodes
     *
     * @param M         Module to consider
     * @param dir       direction to slide module relative to the rest of robot
     *
     * @return          returns if slide affirms to the previous conditions
     *
     */
    public boolean slideIsPossible(Module M, int dir) {
        //TODO, Needswork
        int dirClockwise  = (dir + 1) % 4;
        int dirCounterClock = (dir - 1) % 4;
        if (slideIsPossible(M, (Module)M.getNeighbor(dirClockwise), dir)) {
            return true;
        } 
        if (slideIsPossible(M, (Module)M.getNeighbor(dirCounterClock), dir)) {
            return true;
        } 
        return false;
    }

    /**
     * Performs slide operations.
     * Assumptions made:
     * 1) all modules are in a contracted state between move phases
     * 2) module size is 2
     * 3) dir and neighbor are adjacent directions
     *
     * @param M             Module to slide
     * @param dir           Direction to slide module
     * @param neighborDir   Direction of neighboring modules to slide against
     */
    private void performSlide(Module M, int dir, int neighborDir, int step) {
        assert (M.getSize() == 2);
        List <Unit> mSide = M.getSideUnits(neighborDir);
        Unit back;
        Unit front;
        if (dir == 0 || dir == 3) {
            back = mSide.get(1);
            front = mSide.get(0);
        } else {
            back = mSide.get(0);
            front = mSide.get(1);
        }
        switch(step) {
            //This seems bad... TODO
            case 0:
            case 3:
                //Disconnect front, connect back, disconnect top back
                back.connect(neighborDir);
                front.disconnect(neighborDir);
                //TODO disconnect top back
                //
                //expand interior
                break;
            case 1:
            case 4:
                M.expandInteriorEdges(dir % 2 == 0); //true if vertical 0, 2
                break;
            case 2:
            case 5:
                back.disconnect(neighborDir);
                front.connect(neighborDir);
                break;
            default:
                break;
        }
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
        if (!slideIsPossible(M, dir)) {
            return false;
        }



        

        return true;
    }



}
