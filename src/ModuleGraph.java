package src;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

public class ModuleGraph extends Graph {
	public ModuleGraph(Set<Node> nodes, Set<Edge> edges) {
		super(nodes, edges);
	}

	public static ModuleGraph initFromBools(boolean[][] moduleBools, boolean expanded) {
        //TODO check me
        int w = moduleBools.length;
        int h = moduleBools[0].length;

        Module[][] modules = new Module[w][h];
        Set<Node> moduleSet = new HashSet<Node>();
        Set<Edge> edgeSet = new HashSet<Edge>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (moduleBools[i][j]) {
                    modules[i][j] = new Module(expanded);
                    moduleSet.add(modules[i][j]);
                }
            }
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (moduleBools[i][j]) {
                    for (int dir = 0; dir < Direction.MAX_DIR; dir++) {
                        Coordinate c = new Coordinate(i, j);
                        Coordinate r = c.calcRelativeLoc(dir);
                        if (r.inBounds(w, h) && moduleBools[r.x()][r.y()]) {
                            Edge e = modules[i][j].addNeighbor(modules[r.x()][r.y()], dir, expanded, true);
                            edgeSet.add(e);
                        }
                    }
                }
            }
        }

        return new ModuleGraph(moduleSet, edgeSet);
    }

	public Module[][] toModuleArray() {
        Object[][] grid = this.toGrid(true).getGrid();
        int w = grid.length;
        int h = grid[0].length;

        Module[][] modules = new Module[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                modules[i][j] = (Module) grid[i][j];
            }
        }

        return modules;
    }

    public UnitGraph generateUnitGraph() {
		Set<Node> uNodes = new HashSet<Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Set<Node> mNodes = this.getNodes();
		Set<Edge> mEdges = this.getEdges();

		Module m1, m2;

        // NEEDSWORK: could do with only nodes and getExteriorSubEdges in all direc
		for (Edge mEdge : mEdges) {
			m1 = (Module) mEdge.getN1();
			m2 = (Module) mEdge.getN2();

			uEdges.addAll(m1.getExteriorSubEdges(m2));
		}

		Module m;
		for (Node n : mNodes) {
			m = (Module) n;
			uEdges.addAll(m.getInteriorEdges());
			uNodes.addAll(m.getUnitSet());
		}

		return new UnitGraph(uNodes, uEdges);
	}
}