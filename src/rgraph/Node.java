package rgraph;

import rutils.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Node is the class representing a node with a constant number of edges
 * asociated with it.
 * <p>
 * In version 1.0 the number of edges is exactly 4.
 *
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 */
public class Node {
	private static int counter = 0;

	private final int id;
	// integer 0,1,2,3 for connections in some direction
	private Map<Integer, Edge> connections;

    /** Default node constructor, constructs node with no edges*/
	public Node() {
		id = counter++;
		connections = new HashMap<Integer, Edge>();
	}

    /** Constructs a node with a given map from direction to edge*/
	public Node(Map<Integer, Edge> connections) {
		id = counter++;
		this.connections = connections;
	}

    /**
     * Adds an edge in a specfied direction.
     * @param dir   Direction of new edge
     * @param edge  Edge to add to node
     */
	public void putEdge(int dir, Edge edge) {
		connections.put(dir, edge);
	}

    /**
     * Removes an edge in a direction.
     * <p>
     * If no edge exists, has no effect.
     * @param dir   Direction of edge to remove
     */
    public void removeEdge(int dir) {
        connections.remove(dir);
    }

    /**
     * Gets an edge in a direction.
     * @param dir   Direction of interest
     * @return      Edge in specified direction, null if no edge
     */
	public Edge getEdge(int dir) {
		return connections.get(dir);
	}

    /**
     * Gets neighboring node in a direction.
     * @param dir   Direction of interest
     * @return      neighboring node in direction, null if no neighbor or no edge
     */
	public Node getNeighbor(int dir) {
		Edge e = getEdge(dir);
		if (e == null) {
			return null;
		}
		return e.getOpposite(this);
	}	

    /**
     * Returns unique integer id of node.
     * @return  Integer id is unique among created nodes
     */
	public int getId() {
		return id;
	}
    
    /**
     * Given a 2d array finds own coordinate in array.
     * @param nodes     2d array of nodes
     * @return          2d location of this object in array, (-1,-1) if not found
     */
    public Coordinate findSelfInArray(Node[][] nodes) {
        int w = nodes.length;
        int h = nodes[0].length;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (this.equals(nodes[i][j])) {
                    return new Coordinate(i, j);
                }
            }
        }
        return new Coordinate(-1, -1);
    }

    /**
     * Creates and returns an edge making a neighbor in a relative direction.
     *
     * @param neighbor          Neighboring node to add edge
     * @param direction         Direction of neighbor relative to self
     * @param isExtended        True if neighbor is far away
     * @param isConnected       True if neighbor is connected
     * @return                  Edge used in connection
     */
    public Edge addNeighbor(Node neighbor, int dir, boolean isExtended, boolean isConnected) {
        boolean isVertical = Direction.isVertical(dir);
        Edge e = new Edge(this, neighbor, isExtended, isConnected, isVertical);
        putEdge(dir, e);
        neighbor.putEdge(Direction.opposite(dir), e);
        return e;
    }

    /**
     * Returns if edge exists in a direction.
     * @param dir direction of interest
     * @return    true iff edge exists in direction
     */
    public boolean hasEdge(int dir) {
        return connections.containsKey(dir);
    }
    
    /**
     * returns true if other is a neighbor of this
     */
    public boolean isNeighbor(Node other) {
        if (findNeighborDirection(other) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNeighborInDirection(Node other, int dir) {
        return getNeighbor(dir).equals(other);
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
        for (int dir = 0; dir < Direction.NUM_DIR; dir++) { //check for correct direction
            potentialn = getNeighbor(dir);
            if (potentialn != null && potentialn.equals(neighbor)) {
                neighborDir = dir;
                break;
            }
        }
        return neighborDir;
    }

    /** 
     * Returns true if other node has same id.
     * @param other Node to test
     * @return      true iff nodes are equal.
     */
	public boolean equals(Node other) {
		return (this.id == other.getId());
	}

	public String toString() {
        return "N";
    }
}
