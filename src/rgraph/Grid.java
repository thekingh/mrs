package rgraph;

import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Grid Objects are a spatial representation of the nodes in a robot.
 * <p>
 * A grid holds a 2D array of objects, it is constructed by giving coordinates
 * and objects in a nodes list and an edge list format.
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class Grid {

	private final List<GridObject> nodes;  
	private final Object[][] grid;
	private final int w;
	private final int h;

    /**Constructs a grid from nodes, edges with a width and a height
     * @param nodes List of grid object type nodes to insert
     * @param edges List of grid object type edges to insert
     * @param w     width of grid to create
     * @param h     height of grid to create
     */
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
     * @return String representation of grid
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

    /**Returns a list of the nodes within the grid*/
    public List<GridObject> getNodes() {
        return nodes;
    }

    /**Returns a 2D representation of the Grid*/
    public Object[][] getGrid() {
        return grid;
    }

    /**
     * Returns true if object at specified x, y location is type node
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * @return true object at location is node
     */
    public boolean isNode(int x, int y) {
        return isNode(new Coordinate(x, y));
    }

    /**
     * Returns true if object at specified x, y location is type node
     * @param c coordinate to search
     * @return true object at location is node
     */
    public boolean isNode(Coordinate c) {
        return c.inBounds(w, h) && grid[c.x()][c.y()] instanceof Node;
    }

    /**
     * Returns true if object at specified x, y location is type edge
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * @return true object at location is edge
     */
    public boolean isEdge(int x, int y) {
        return isEdge(new Coordinate(x, y));
    }

    /**
     * Returns true if object at specified x, y location is type edge
     * @param c coordinate to search
     * @return true object at location is edge
     */
    public boolean isEdge(Coordinate c) {
        return c.inBounds(w, h) && grid[c.x()][c.y()] instanceof Edge;
    }

    /**
     * Returns object at specified x, y location
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * @return object at location in grid
     */
    public Object at(int x, int y) {
        return at(new Coordinate(x,y));
    }

    /**
     * Returns object at specified coordinate
     * @param c coordinate to search
     * @return object at coordinate in grid
     */
    public Object at(Coordinate c) {
        if (!c.inBounds(w, h)) {
            return null;
        }
        return grid[c.x()][c.y()];
    }

    /**
     * produces a new GridObject, returns null if out of bounds, or no object.
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * @return grid object containing the object at the specified location in the grid
     */
    public GridObject getGridObject(int x, int y) {
        return getGridObject(new Coordinate(x, y));
    }

    /**
     * produces a new GridObject, returns null if out of bounds, or no object.
     * @param c coordinate to find in the grid
     * @return grid object containing the object at the specified location in the grid
     */
    public GridObject getGridObject(Coordinate c) {
        if (!c.inBounds(w, h)) {
            return null;
        }
        return new GridObject(grid[c.x()][c.y()], c);
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

