
package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;



public class Robot {
	private Graph moduleGraph;
	private Graph unitGraph;
	private Grid moduleGrid;
	private Grid unitGrid;

	private boolean isModuleGridCurrent;
	private boolean isUnitGridCurrent;


	public Robot(Graph moduleGraph) {
		// what to use?
		this.moduleGraph = moduleGraph;
	}

	public Graph getUnitGraph() {
		generateUnitGraph();
		return unitGraph;
	}


	// generates unit graph from module graph
	// NEEDSWORK: want to return unit graph? record status?
	private void generateUnitGraph() {
		Map<Integer, Node> uNodes = new HashMap<Integer, Node>(); 
		Set<Edge> uEdges = new HashSet<Edge>();
		Map<Integer, Node> mNodes = moduleGraph.getNodes();
		Set<Edge> mEdges = moduleGraph.getEdges();

		Module m1, m2;

		for (Edge mEdge : mEdges) {
			m1 = (Module) mEdge.getN1();
			m2 = (Module) mEdge.getN2();

			// NEEDSWORK: adding all the exterior edges here seems weird???
			// maybe add when we connect modules?
			uEdges.addAll(m1.addExteriorSubEdges(m2));
		}

		Module m;
		for (Node n : mNodes.values()) {
			m = (Module) n;
			uEdges.addAll(m.getInteriorEdges());

			uNodes.putAll(m.getUnitMap());
		}

		unitGraph = new Graph(uNodes, uEdges);
	}



    public static void main(String[] args) {
        System.out.println("Hello World!");
        return;
    }
}
