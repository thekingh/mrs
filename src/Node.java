/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * abstract class for use in graph
 */

package src;

import java.util.Map;
import java.util.HashMap;

public abstract class Node {
	private static int counter = 0;

	private final int id;
	// integer 0,1,2,3 for connections in some direction
	private Map<Integer, Edge> connections;

	public Node() {
		id = counter++;
		connections = new HashMap<Integer, Edge>();
	}

	public Node(Map<Integer, Edge> connections) {
		id = counter++;
		this.connections = connections;
	}

	public void putEdge(int direction, Edge edge) {
		connections.put(direction, edge);
	}

	public Edge getEdge(int direction) {
		return connections.get(direction);
	}

	public Node getNeighbor(int direction) {
		Edge e = getEdge(direction);
		if (e == null) {
			return null;
		}
		return e.getOpposite(this);
	}	

	public int getId() {
		return id;
	}

	public boolean equals(Node n) {
		return (this.id == n.getId());
	}

	public abstract String toString();
}




