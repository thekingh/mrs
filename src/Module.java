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
	private static final boolean EXTENDED_EDGES = false;
	private static final boolean CONNECTED_EDGES = true;

	private static int size = 2;
	private Module inside;
	private final Set<Edge> horizontalEdges;
    private final Set<Edge> verticalEdges;
	private Unit[][] units;

	// TODO make constructor with isExtended, isConnected arguments
	// then have this call it with defaults
	public Module() {
		super();
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

    public Unit getUnitInQuadrant(int dir1, int dir2) {
        assert (size == 2);
        //TODO fix or get rid super ugly
        switch(4 * dir1 + dir2) {
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
/*
            case 1:
                return getUnit(0,1);
            case 3: 
                return getUnit(0,0);
            case 4:
                return getUnit(0,1);
            case 6:
                return getUnit(1,1);
            case 9:
                return getUnit(1,1);
            case 11:
                return getUnit(1,0);
            case 12:
                return getUnit(0,0);
            case 14:
                return getUnit(1,0);
            default:
                return null;
*/
        }
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
    public Set<Edge> addExteriorSubEdges(Module neighbor) {
        boolean isExtended;
        boolean isConnected;
        Set<Edge> toReturn = new HashSet<Edge>();
        Edge e;

        int dir = findNeighborDirection(neighbor);
        if (dir == -1) {
            //TODO THROW ERROW
           System.out.println("HALP WE BROKE IT");
        }
        e = getEdge(dir);

        List<Unit> thisSideUnits     = getSideUnits(dir);
        List<Unit> neighborSideUnits = neighbor.getSideUnits((dir + 2) % 4);
        Unit u;
        Unit n;
        Edge newe;

        isExtended = e.isExtended();
        isConnected = e.isConnected();
        for (int i = 0; i < size; i++) {
            u = thisSideUnits.get(i);
            n = neighborSideUnits.get(i);
            newe = u.addNeighbor(n, dir, isExtended, isConnected);
            toReturn.add(newe);
        }
        return toReturn;
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
        List<Unit> sideUnits = getSideUnits(dir);

        for (Unit u : sideUnits) {
            Edge sideEdge = u.getEdge(dir);
            sideEdge.setIsExtended(true);
        }
    }

	// NEEDSWORK: print all of the units?
	public String toString() {
		//return "M";
		return String.format("%d ", getId());
	}
}
