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

	private final int size;
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

		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - 1; j++) {
				u = this.units[i][j];

				// add right edge with neighbor
				rightNeighbor = this.units[i+1][j];
				eLeft = u.addNeighbor(rightNeighbor, 1, EXTENDED_EDGES, 
					CONNECTED_EDGES);
				this.interiorEdges.add(eLeft);

				// add down edge with neighbor
				downNeighbor = this.units[i][j+1];
				eDown = u.addNeighbor(downNeighbor, 2, EXTENDED_EDGES,
					CONNECTED_EDGES);
				this.interiorEdges.add(eDown);
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

	// NEEDSWORK: print all of the units?
	public String toString() {
		return "M";
	}
}
