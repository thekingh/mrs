package rgraph;

import rutils.*;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a logical grouping of units, restricted to 4 units in version 1.0.
 * <p>
 * A module is a logical grouping of a 2x2 set of nodes. This is the building
 * block for all known reconfiguration algorithms as modules are a very helpful
 * abstraction allowing movements that single units cannot accomplish on their
 * own i.e. slides etc.
 * <p>
 * Modules extend the class Node, as it is helpful to view modules as belonging
 * to a graph representation of those modules.
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class Module extends Node {
	// TODO may want to take these in constructor for module
	private static boolean EXTENDED_EDGES = false;
	private static final boolean CONNECTED_EDGES = true;

	private static int size = 2;
	private final Set<Edge> horizontalEdges;
    private final Set<Edge> verticalEdges;
	private Unit[][] units;

    /** Default module constructor, defaults to contracted.*/
    public Module() {
        this(false);
    }

    /** Constructs a 2x2 module.
     * @param expanded true value creates a module which is size 3x3 with
     *                 extended arms between units
     */
	public Module(boolean expanded) {
		super();
        EXTENDED_EDGES = expanded;
		this.units = new Unit[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.units[i][j] = new Unit();
			}
		}

		// adding all interior edges to units
		Unit u;
		Unit rightNeighbor, downNeighbor; // future neighbor to unit
		Edge eLeft, eDown;
		this.verticalEdges = new HashSet<Edge>();
		this.horizontalEdges = new HashSet<Edge>();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				u = this.units[i][j];

				// add right edge with neighbor
				if (i < size - 1) {
					rightNeighbor = this.units[i+1][j];
					eLeft = u.addNeighbor(rightNeighbor, 1, EXTENDED_EDGES, 
						CONNECTED_EDGES);
					this.horizontalEdges.add(eLeft);
				}

				// add down edge with neighbor
				if (j < size - 1) {
					downNeighbor = this.units[i][j+1];
					eDown = u.addNeighbor(downNeighbor, 2, EXTENDED_EDGES,
						CONNECTED_EDGES);
					this.verticalEdges.add(eDown);
				}
			}
		}
	}

    /** Returns size of the module.*/
	public int getSize() {
		return size;
	}

    /** Returns all edges between units in the module*/
	public Set<Edge> getInteriorEdges() {
        Set<Edge> interiorEdges = new HashSet<Edge>(horizontalEdges);
        interiorEdges.addAll(verticalEdges);
		return interiorEdges;
	}

    /** Returns a set of all units in the module*/
	public Set<Unit> getUnitSet() {
		Set<Unit> u = new HashSet<Unit>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				u.add(units[i][j]);
			}
		}
		return u;
	}

    /**
     * Gets a list containing the units on a side of the module.
     * <p>
     * The list is ordered from left to right or from top to bottom (depending)
     * on direction. This means that with two neighboring modules, side units
     * can be matched up, and edges between modules are easy to create by
     * iterating over side units.
     * @param dir           Indicates which side of the module to get. 
     *                      0=North, 1=East, 2=South, 3=West
     * @return              List of Unit objects
     */
    public List<Unit> getSideUnits(int dir) {
        List<Unit> toReturn = new ArrayList<Unit>();
        int mini = 0;
        int minj = 0;
        int maxi = size;
        int maxj = size;
        switch(dir) {
            case 0: // North
                maxj = 1;
                break;
            case 1: // East
                mini = size - 1;
                break;
            case 2: // South
                minj = size - 1;
                break;
            case 3: // West
                maxi = 1;
                break;
            default:
                break;
        }
        for (int i = mini; i < maxi; i++) {
            for (int j = minj; j <maxj; j++) {
                toReturn.add(units[i][j]);
            }
        }
        return toReturn;
    }

    /**
     * Returns array of units from a starting quadrant, going in direction starting
     * in dir1, and moving towards dir2.
     * <p>
     * This method only applies for Module size of 2.
     * <p>
     * Behavior when dir1 == dir2 or when dir1 == Direction.opposite(dir2) is
     * undefined.
     * @param dir1      Initial direction of sweep
     * @param dir2      Secondary direction of sweep
     * @return array of units 
     */
    public Unit[] getUnitsFrom(int dir1, int dir2) {
        assert (size == 2);
        Unit[] units = new Unit[4];
        units[0] = getUnitInQuadrant(dir1, dir2);
        units[1] = getUnitInQuadrant(dir1, Direction.opposite(dir2));
        units[2] = getUnitInQuadrant(Direction.opposite(dir1), Direction.opposite(dir2));
        units[3] = getUnitInQuadrant(Direction.opposite(dir1), dir2);
        return units;
    }

    /**
     * Gets A unit given two directions determining the quadrent of the unit.
     * <p>
     * This method only applies for Module size of 2.
     * <p>
     * Behavior when dir1 == dir2 or when dir1 == Direction.opposite(dir2) is
     * undefined. swapping the two direction parameters results in the same 
     * return value (as a symetric operation specifies the same quadrant).
     * @param dir1 First direction vector
     * @param dir2 Second direction vector
     * @return Unit from a quadrant of the module
     */
    public Unit getUnitInQuadrant(int dir1, int dir2) {
        assert (size == 2);
        //TODO fix or get rid super ugly
        switch(Direction.NUM_DIR * dir1 + dir2) {
            case 3:
            case 12:
                return units[0][0];
            case 11:
            case 14:
                return units[0][1];
            case 1:
            case 4:
                return units[1][0];
            case 6:
            case 9:
                return units[1][1];
            default:
                return null;
        }
    }

    /**
     * Returns true if this module can slide in a direction.
     * <p>
     * Conditions:
     * <ol>
     *  <li> No module in direction of slide
     * Module can slide if neighbor in one of two adjacent directions of slide
     * and if no module in direction of slide.
     *
     * Conditions:
     *  No Module in dir
     *      
     */
/*    public boolean canSlide(int dir) {*/
/*        */
/*        Node m1 = getNeighbor(Direction.right(dir));*/
/*        Node m2 = getNeighbor(Direction.left(dir));*/
/**/
/*        boolean moveOnM1 = (m1 != null) && (m1.hasNeighborInDirection(dir));*/
/*        boolean moveOnM2 = (m2 != null) && (m2.hasNeighborInDirection(dir));*/
/**/
/*        return (moveOnM1 || moveOnM2) && !hasNeighborInDirection(dir);*/
/*    }*/


    /** Adds a neighboring module in a specified direction, with extension and connection args
     * @param neighbor      Neighboring module to connect to
     * @param dir           Direction of neighbor module
     * @param isExtended    true if distance between this and neighbor is 1
     * @param isConnected   true to connect modules
     * @return              the edge created
     */
    public Edge addNeighbor(Module neighbor, int dir, boolean isExtended, boolean isConnected) {
        boolean isVertical = Direction.isVertical(dir);
        Edge e = new Edge(this, neighbor, isExtended, isConnected, isVertical);
        putEdge(dir, e);
        neighbor.putEdge(Direction.opposite(dir), e);
        addExteriorSubEdges(neighbor, dir, isExtended, isConnected);
        return e;
    }

    /**
     * Adds all the smaller arms given two modules with an edge between them.
     * Note that there must exist an edge between them otherwise this method
     * will throw an error
     *
     * @param neighbor      Neigbor to connect to.
     *
     * @return              Set<Edge> of edges added
     */
    private Set<Edge> addExteriorSubEdges(Module neighbor, int dir,
        boolean isExtended, boolean isConnected) {

        Set<Edge> toReturn = new HashSet<Edge>();
        Edge e;

        e = getEdge(dir);

        List<Unit> thisSideUnits     = getSideUnits(dir);
        List<Unit> neighborSideUnits = neighbor.getSideUnits(Direction.opposite(dir));
        Unit u;
        Unit n;
        Edge newe;

        for (int i = 0; i < size; i++) {
            u = thisSideUnits.get(i);
            n = neighborSideUnits.get(i);
            newe = u.addNeighbor(n, dir, isExtended, isConnected);
            toReturn.add(newe);
        }
        return toReturn;
    }

    /**
     * Returns a list containing all the exterior edges in a direction.
     * @param dir       direction of interest
     * @return          List of edges between units in that direction. 
     *                  Always supplied up to down or left to right.
     */
    public List<Edge> getExteriorSubEdges(int dir) {
        List<Edge> sideEdges = new ArrayList<Edge>();

        List<Unit> sideUnits = getSideUnits(dir);
        for (Unit u : sideUnits) {
            sideEdges.add(u.getEdge(dir));
        }

        return sideEdges;
    }

    /**
     * Returns a list containing all the exterior edges in a direction.
     * @param neighbor  Neighboring module of interest
     * @return          List of edges between units in that direction. 
     *                  Always supplied up to down or left to right.
     */
    public List<Edge> getExteriorSubEdges(Module neighbor) {
        return getExteriorSubEdges(this.findNeighborDirection(neighbor));
    }

    /**
     * Gets the interior edges either all horizontal or all vertical.
     *
     * @param vertical true gets vertical edges, horizontal otherwise.
     * @return         set of all edges in between units of the module in either
     *                 the horizontal or vertical directions.
     */
    public Set<Edge> getInteriorEdges(boolean vertical) {
        if (vertical) {
            return verticalEdges;
        } else {
            return horizontalEdges;
        }
    }

    private void modifyInteriorEdges(boolean vertical, boolean extend) {
        Set<Edge> interior = getInteriorEdges(vertical);
        for (Edge e : interior) {
            e.setIsExtended(extend);
        }
    }

    /** Expands the module in the vertical or horizontal direction */
    public void expandInteriorEdges(boolean vertical) {
        modifyInteriorEdges(vertical, true);
    }

    /** Contracts the module in the vertical or horizontal direction */
    public void contractInteriorEdges(boolean vertical) {
        modifyInteriorEdges(vertical, false);
    }

    /** Expands exterior edges in a direction*/
    public void expandExteriorEdges(int dir) {
        for (Edge e : getExteriorSubEdges(dir)) {
            if (e != null) {
                e.setIsExtended(true);
            }
        }
    }

    /** Contracts exterior edges in a direction*/
    public void contractExteriorEdges(int dir) {
        for (Edge e : getExteriorSubEdges(dir)) {
            if (e != null) {
                e.setIsExtended(false);
            }
        }
    }

    /**
     * Swaps Units in the module array, useful for cleaning up after
     * complicated movements, where units may be rearranged within the module.
     * @param u1    First unit to swap
     * @param u2    Second unit to swap
     */
    public void swapUnits(Unit u1, Unit u2) {
        Coordinate a = u1.findSelfInArray(units);
        Coordinate b = u2.findSelfInArray(units);
        swapUnitsFromCoords(a.x(), a.y(), b.x(), b.y());
    }

    private void swapUnitsFromCoords(int x1, int y1, int x2, int y2) {
        Unit temp = units[x1][y1];
        units[x1][y1] = units[x2][y2];
        units[x2][y2] = temp;
    }

	// NEEDSWORK: print all of the units?
    /** Debug method, prints the module to screen as "M"*/
	public String toString() {
		return "M";
/*		return String.format("%d ", getId());*/
	}
}
