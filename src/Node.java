/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * abstract class for use in graph
 */

package src;

import java.util.Map;
import java.util.HashMap;

/**
 * Node is the abstract class representing a vertex with a number of edges
 * asociated 
 *
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 *
 */
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

    public void removeEdge(int dir) {
        connections.remove(dir);
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

    /**
     * Creates and returns an edge making a neighbor in a relative direction
     *
     * @param neighbor          Neighboring node to add edge
     * @param direction         Direction of neighbor relative to self
     * @param isExtended        True if neighbor is far away
     * @param isConnected       True if neighbor is connected
     *
     * @return                  Edge used in connection
     *
     */
    public Edge addNeighbor(Node neighbor, int direction, boolean isExtended, boolean isConnected) {
        boolean isVertical;
        if (direction == 0 || direction == 2) { //NEEDSWORK is sort of poor form
            isVertical = true;
        } else {
            isVertical = false;
        }
        Edge e = new Edge(this, neighbor, isExtended, isConnected, isVertical);
        putEdge(direction, e);
        neighbor.putEdge((direction + 2) % 4, e);
        return e;
    }

    /**
     * Given a node, attempts to find the direction to that node in the set
     * {0=North, 1=East, 2=South, 3=West}.
     * @param neighbor          Neighboring node to find
     *
     * @return                  direction of neighboring node. If neighbor is
     *                          not found as a neighbor, returns -1
     */
    public int findNeighborDirection(Node neighbor) {
        Node potentialn;
        int neighborDir = -1;
        for (int dir = 0; dir < 4; dir++) { //check for correct direction
            potentialn = getNeighbor(dir);
            if (potentialn != null && potentialn.equals(neighbor)) {
                neighborDir = dir;
                break;
            }
        }
        return neighborDir;
    }

	public boolean equals(Node n) {
		return (this.id == n.getId());
	}

	public abstract String toString();
}
