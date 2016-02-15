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
public class ModuleGraph extends Graph {

    /** Constructs a Module Graph from a Set of Modules and Edges*/
	public ModuleGraph(Set<Node> nodes, Set<Edge> edges) {
		super(nodes, edges);
	}

    /**
     * Factory method for Module Graph type.
     * <p>
     * Initializes a module graph from a 2d boolean array
     * @param moduleBools   2D boolean array of module locations
     * @param expanded      If true, all modules are created expanded (i.e. unit
     *                      size of 3x3.
     * @return ModuleGraph object
     */
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

    /**
     * Gets a 2D array of modules, a spatial representation of the graph.
     * @return 2D array of modules
     */
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

    /**
     * Constructs a UnitGraph from the Module Graph.
     * @return Unit graph representation
     */
    public UnitGraph generateUnitGraph() {
		Set<Node> uNodes = new HashSet<Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Set<Node> mNodes = this.getNodes();
		Set<Edge> mEdges = this.getEdges();

        //Add edges between modules
        Module m1, m2;
		for (Edge mEdge : mEdges) {
            Pair<Node, Node> ms = mEdge.getNodes();
            m1 = (Module) ms.a;
            m2 = (Module) ms.b;
			uEdges.addAll(m1.getExteriorSubEdges(m2));
		}

        //Add edges interior to modules, and module units
		Module m;
		for (Node n : mNodes) {
			m = (Module) n;
			uEdges.addAll(m.getInteriorEdges());
			uNodes.addAll(m.getUnitSet());
		}

		return new UnitGraph(uNodes, uEdges);
	}
}
