/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * robot module with n*n
 */

package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Module extends Node {
	// may want to take these in constructor for module
	private static final boolean EXTENDED_EDGES = false;
	private static final boolean CONNECTED_EDGES = true;

	private static int size;
	private Module inside;
	private Set<Edge> interiorEdges;
	private Unit[][] units;

	// TODO make constructor with isExtended, isConnected arguments
	// then have this call it with defaults
	public Module(int size) {
		super();
		this.size = size;
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
		this.interiorEdges = new HashSet<Edge>();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				u = this.units[i][j];

				// add right edge with neighbor
				if (i < size - 1) {
					rightNeighbor = this.units[i+1][j];
					eLeft = u.addNeighbor(rightNeighbor, 1, EXTENDED_EDGES, 
						CONNECTED_EDGES);
					this.interiorEdges.add(eLeft);
				}

				// add down edge with neighbor
				if (j < size - 1) {
					downNeighbor = this.units[i][j+1];
					eDown = u.addNeighbor(downNeighbor, 2, EXTENDED_EDGES,
						CONNECTED_EDGES);
					this.interiorEdges.add(eDown);
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
     * returns a list
     */
    public List<Unit> getSideUnits(int dir) {
        List<Unit> toReturn = new ArrayList<Unit>();
        int mini = 0;
        int minj = 0;
        int maxi = size;
        int maxj = size;
        switch(dir) {
            case 0:
                maxj = 1;
                break;
            case 1:
                mini = size - 1;
                break;
            case 2:
                minj = size - 1;
                break;
            case 3:
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
     * Adds all the smaller arms given two modules with an edge between them
     *
     * @param neighbor      
     * @return              Set<Edge> of edges added
     */
    public Set<Edge> addExteriorSubEdges(Module neighbor) {
        int neighborDir = 0;
        boolean isExtended;
        boolean isConnected;
        Set<Edge> toReturn = new HashSet<Edge>();
        Edge e;
        Node potentialn;
        for (int dir = 0; dir < 4; dir++) { //check for correct direction
            potentialn = getNeighbor(dir);
            if (potentialn != null && potentialn.equals(neighbor)) {
                neighborDir = dir;
                break;
            }
        }
        e = getEdge(neighborDir);
        isExtended = e.isExtended();
        isConnected = e.isConnected();
        List<Unit> thisSideUnits     = getSideUnits(neighborDir);
        List<Unit> neighborSideUnits = neighbor.getSideUnits((neighborDir + 2) % 4);

        Unit u;
        Unit n;
        Edge newe;
        for (int i = 0; i < size; i++) {
            u = thisSideUnits.get(i);
            n = neighborSideUnits.get(i);
            newe = u.addNeighbor(n, neighborDir, isExtended, isConnected);
            toReturn.add(newe);
        }
        return toReturn;
    }

    public Map<Integer, Unit> getUnitMap() {
        Map<Integer, Unit> toReturn = new HashMap<Integer, Unit>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
                Unit u = units[i][j];
				toReturn.put(u.getId(), u);
			}
		}
        return toReturn;
    }

	// NEEDSWORK: print all of the units?
	public String toString() {
		return "M";
	}
}
