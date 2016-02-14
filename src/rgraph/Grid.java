/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * grid representation of a robot
 */

package rgraph;

import rutils.*;


import java.util.List;
import java.util.ArrayList;


public class Grid {

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
            //TODO changed this ^^
		}
	}

    /**
     * Produces string representation of the grid, with positive x right
     * positive y up.
     */
	public String toString() {
		String str = "";
        for (int j = h - 1; j >= 0; j--) {
            for (int i = 0; i < w; i++) {
				Object o = grid[i][j];
				if (o == null) {
					str += " ";
				} else {
					str += o.toString();
				}
			}
			str += "\n";
		}
		return str;
	}

    public List<GridObject> getNodes() {
        return nodes;
    }

    public Object[][] getGrid() {
        return grid;
    }

    public boolean inBounds(int x, int y) {
        return (x >= 0) && (x < w) && (y >= 0) && (y < h);
    }

    public boolean inBounds(Coordinate c) {
        return inBounds(c.x(), c.y());
    }

    public boolean isNode(int x, int y) {
        return inBounds(x, y) && grid[x][y] instanceof Node;
    }

    public boolean isNode(Coordinate c) {
        return isNode(c.x(), c.y());
    }

    public boolean isEdge(int x, int y) {
        return inBounds(x, y) && grid[x][y] instanceof Edge;
    }

    public boolean isEdge(Coordinate c) {
        return isEdge(c.x(), c.y());
    }

    public Object at(int x, int y) {
        if (!inBounds(x, y)) {
            return null;
        }
        return grid[x][y];
    }

    public Object at(Coordinate c) {
        return at(c.x(), c.y());
    }

    /**
     * produces a new GridObject, returns null if out of bounds, or no object
     */
    public GridObject getGridObject(int x, int y) {
        if (!inBounds(x, y)) {
            return null;
        }
        return new GridObject(grid[x][y], new Coordinate(x, y));
    }

    public GridObject getGridObject(Coordinate c) {
        return getGridObject(c.x(), c.y());
    }

    /**
     * finds the closest neighboring node in a direction, return NULL if further
     * than MAX_DIST = 1.
     * @param c     root coordinate
     * @param dir   direction to search
     */
    public GridObject findClosestNode(Coordinate c, int dir) {
        final int MAX_DIST = 1;
        Coordinate cp;
        for (int i = 0; i <= MAX_DIST; i++) {
            cp = c.calcRelativeLoc(dir, i);
            if (isNode(cp)) { //auto bounds checks
                return getGridObject(cp);
            }
        }
        return null;
    }
}

