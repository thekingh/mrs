/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 */

package src;

import java.util.Map;
import java.util.HashMap;

public class Graph {

	private Map<Integer, Node> nodes;
	private Map<Integer, Edge> edges;

	public Graph() {
		//Graph(new HashMap<Integer, Node>(), new HashMap<Integer, Edge>());
		this.nodes = new HashMap<Integer, Node>();
		this.edges = new HashMap<Integer, Edge>();
	}

	public Graph(Map<Integer, Node> nodes, Map<Integer, Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	// TODO
	public Grid toGrid() {
		return null;
	}
}




