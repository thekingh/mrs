/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * robot module with n*n
 */

package src;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Module extends Node {
	// may want to take these in constructor for module
	private static boolean EXTENDED_EDGES = false;
	private static final boolean CONNECTED_EDGES = true;

	private static int size = 2;
	private Module inside;
	private final Set<Edge> horizontalEdges;
    private final Set<Edge> verticalEdges;
	private Unit[][] units;

    public Module() {
        this(false);
    }

	// TODO make constructor with isExtended, isConnected arguments
	// then have this call it with defaults
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

	public int getSize() {
		return size;
	}

	public Module getInside() {
		return inside;
	}

	public void putInside(Module m) {
		inside = m;
	}

	public boolean hasInside() {
		return (inside != null);
	}

	public Set<Edge> getInteriorEdges() {
        Set<Edge> interiorEdges = new HashSet<Edge>(horizontalEdges);
        interiorEdges.addAll(verticalEdges);
		return interiorEdges;
	}

	public Unit getUnit(int i, int j) {
		return units[i][j];
	}

	// might not be needed
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
     * gets a list containing the units on a side of the module.
     * The list is ordered from left to right or from top to bottom (depending)
     * on direction. This means that with two neighboring modules, side units
     * can be matched up, ad we can easily create edges between modules
     *
     * @param dir           Indicates which side of the module to get. 
     *                      0=North, 1=East, 2=South, 3=West
     *
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

    // TODO: change name: not always clockwise
    /**
     * Returns array of units from a starting quadrant, going in direction starting
     * in dir1, and moving towards dir2 i.e. either clocwise or counter clockwise.
     */
    public Unit[] getUnitsFrom(int dir1, int dir2) {
        Unit[] units = new Unit[4];
        units[0] = getUnitInQuadrant(dir1, dir2);
        units[1] = getUnitInQuadrant(dir1, Direction.opposite(dir2));
        units[2] = getUnitInQuadrant(Direction.opposite(dir1), Direction.opposite(dir2));
        units[3] = getUnitInQuadrant(Direction.opposite(dir1), dir2);
        return units;
    }

    public Unit getUnitInQuadrant(int dir1, int dir2) {
        assert (size == 2);
        //TODO fix or get rid super ugly
        switch(Direction.MAX_DIR * dir1 + dir2) {
            case 3:
            case 12:
                return getUnit(0,0);
            case 11:
            case 14:
                return getUnit(0,1);
            case 1:
            case 4:
                return getUnit(1,0);
            case 6:
            case 9:
                return getUnit(1,1);
            default:
                return null;
        }
    }

    /**
     * Module can slide if neighbor in one of two adjacent directions of slide
     * and if no module in direction of slide.
     *
     * Conditions:
     *  No Module in dir
     *      
     */
    public boolean canSlide(int dir) {
        
        Node m1 = getNeighbor(Direction.right(dir));
        Node m2 = getNeighbor(Direction.left(dir));

        boolean moveOnM1 = (m1 != null) && (m1.hasNeighborInDirection(dir));
        boolean moveOnM2 = (m2 != null) && (m2.hasNeighborInDirection(dir));

        return (moveOnM1 || moveOnM2) && !hasNeighborInDirection(dir);
    }

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

    public List<Edge> getExteriorSubEdges(int dir) {
        List<Edge> sideEdges = new ArrayList<Edge>();

        List<Unit> sideUnits = getSideUnits(dir);
        for (Unit u : sideUnits) {
            sideEdges.add(u.getEdge(dir));
        }

        return sideEdges;
    }

    public List<Edge> getExteriorSubEdges(Module neighbor) {
        return getExteriorSubEdges(this.findNeighborDirection(neighbor));
    }


    /**
     * gets the interior edges either all horizontal or all vertical.
     *
     * @param 
     * @param
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

    public void expandInteriorEdges(boolean vertical) {
        modifyInteriorEdges(vertical, true);
    }

    public void contractInteriorEdges(boolean vertical) {
        modifyInteriorEdges(vertical, false);
    }

    // TODO: add checks to make sure edge exists
    public void expandExteriorEdges(int dir) {
        for (Edge e : getExteriorSubEdges(dir)) {
            e.setIsExtended(true);
        }
    }

    public void swapUnits(Unit u1, Unit u2) {
        Coordinate a = findCoordinate(u1);
        Coordinate b = findCoordinate(u2);
        swapUnitsFromCoords(a.x(), a.y(), b.x(), b.y());
    }

    private Coordinate findCoordinate(Unit u) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (units[i][j].equals(u)) {
                    return new Coordinate(i, j);
                }
            }
        }

        return new Coordinate(-1, -1);
    }

    private void swapUnitsFromCoords(int x1, int y1, int x2, int y2) {
        Unit temp = units[x1][y1];
        units[x1][y1] = units[x2][y2];
        units[x2][y2] = temp;
    }

	// NEEDSWORK: print all of the units?
	public String toString() {
		return "M";
/*		return String.format("%d ", getId());*/
	}
}
