package src;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

public class UnitGraph extends Graph {
	public UnitGraph(Set<Node> nodes, Set<Edge> edges) {
		super(nodes, edges);
	}

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