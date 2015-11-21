/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * grid representation of a robot
 */

package src;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Grid {

	// map from node id to coordinate
	private final List<GridObject> nodes;  
	private final Object[][] grid;
	private final int w;
	private final int h;

	public Grid() {
		nodes = new ArrayList<GridObject>();
		grid = null;
		w = 0;
		h = 0;
	}

	public Grid(List<GridObject> nodes, List<GridObject> edges, int w, int h) {
		this.nodes = nodes;
		this.w = w;
		this.h = h;
		this.grid = new Object[w][h];

		List<GridObject> merged = new ArrayList<GridObject>();
		merged.addAll(nodes);
		merged.addAll(edges);

		// place all objects at proper place in grid based on cartesian location
		for (GridObject o : merged) {
			Coordinate coord = o.c();
			this.grid[coord.x()][coord.y()] = o.o();
		}
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < w; i++) {
			// NEEDSWORK: do we want positive y down
			for (int j = h-1; j >= 0; j++) {
				Object o = grid[i][j];
				if (o == null) {
					str += " ";
				} else {
					str += o.toString();
				}
			}
			str += "/n";
		}

		return str;
	}

}



