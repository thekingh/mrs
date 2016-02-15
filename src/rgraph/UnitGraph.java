package rgraph;

import rutils.*;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

/**
 * Module version of Graph class.
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class UnitGraph extends Graph {

    /** Constructs a Unit Graph from a Set of Modules and Edges*/
	public UnitGraph(Set<Node> nodes, Set<Edge> edges) {
		super(nodes, edges);
	}

    /**
     * Returns a 2D array representation of the unit graph.
     * @return 2D array of units.
     */
	public Unit[][] toUnitArray() {
        Object[][] grid = this.toGrid(true).getGrid();
        int w = grid.length;
        int h = grid[0].length;

        Unit[][] modules = new Unit[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                modules[i][j] = (Unit) grid[i][j];
            }
        }

        return modules;
    }
}
