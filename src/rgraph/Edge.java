package rgraph;

import rutils.*;

import java.lang.Math;
//TODO get rid of isconnected

public class Edge {
	private static int counter = 0;
	private final int id;
	private boolean isExtended;
	private boolean isConnected;
	private final boolean isVertical;
	private final Node n1;
	private final Node n2;

	// NEEDWORK: change ordering of args?
	public Edge(Node n1, Node n2, boolean isExtended, boolean isConnected, boolean isVertical) {
		id = counter++;
		this.isExtended = isExtended;
		this.isConnected = isConnected;
		this.isVertical = isVertical;
		this.n1 = n1;
		this.n2 = n2;
	}

    /**
     * @return integer id of edge
     */
	public int getId() {
		return id;
	}

    /**Returns true if edge is extended */
	public boolean isExtended() {
	    return isExtended;
	}

    /**Sets the edge extension
     * @param isExtended new value for is extended
     */
	public void setIsExtended(boolean isExtended) {
	    this.isExtended = isExtended;
	}

    /**Returns true if edge is connected */
	public boolean isConnected() {
	    return isConnected;
	}

    /**Sets the edge connectedness
     * @param isConnected new value for is connected
     */
	public void setIsConnected(boolean isConnected) {
	    this.isConnected = isConnected;
	}

    /**Returns true if edge is vertical */
	public boolean isVertical() {
	    return isVertical;
	}

    /**
     * returns both endpoints of the edge.
     * <p>
     * While the order of the nodes is not specified, the order of the nodes for
     * a given edge object will never change.
     * @return A pair of nodes representing the endpoints of the edge
     */
    public Pair<Node, Node> getNodes() {
        return new Pair<Node, Node>(n1, n2);
    }

    /**returns first node of edge*/
    public Node getN1() {
        return n1;
    }

    /**returns second node of edge*/
    public Node getN2() {
        return n2;
    }

    /**
     * Given a node, returns the node on the other end of the edge.
     * @param n Reference node
     * @return Node object to return
     */
	public Node getOpposite(Node n) {
		if (n.equals(n1)) {
			return n2;
		} else {
			return n1;
		}
	}

    /**
     * Returns edge equality.
     * <p>
     * Returns true iff o is of type edge and both connect the same two nodes.
     * @param o object to compare equality
     * @return true if edges are equal
     */
    @Override
    public boolean equals(final Object o) {
        if(o == null) {
            return false;
        }

        if(!(o instanceof Edge)) {
            return false;
        }

        Edge e = (Edge)o;

		return ((n1.equals(e.getN1()) && n2.equals(e.getN2())) ||
				(n1.equals(e.getN2()) && n2.equals(e.getN1())));
    }

    /**
     * Returns a hashcode for the edge object, unique for two given nodes.
     * <p>
     * Two edges have the same hashcode iff the both edges connect the same
     * two nodes.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        String n1_s = Integer.toString(n1.getId());
        String n2_s = Integer.toString(n2.getId());

        if(n1.getId() < n2.getId()) {
            return ((n1_s + "," + n2_s).hashCode());
        } else {
            return ((n2_s + "," + n1_s).hashCode());
        }
    }

	public String toString() {
		if (isVertical) {
			return "|";
		} else {
			return "-";
		}
	}

    private int getExtensionLength() {
        return isExtended ? 1 : 0;
    }

    /**
     * removes references to an edge from nodes, does not delete the edge.
     * @return true if successful
     */
    public boolean removeFromNodes() {
        //TODO perhaps faster? garbage collection?
        int n1dir = n1.findNeighborDirection(n2);
        int n2dir = Direction.opposite(n1dir);
        n1.removeEdge(n1dir);
        n2.removeEdge(n2dir);
        return true;
    }

    /**
     * isValidEdge checks the spatial validity of an edge.
     * An edge is considered valid if the following conditions are met where an
     * edge has endpoints n1 and n2, note that order doesn't matter, i.e. if
     * the opposite assignment satisfies the following then the edge is also valid
     * 1) n1 is located at c1, n2 is located at c2
     * 2) if edge is extended |c2 - c1| == 2 else |c2 - c1| == 1
     * 3) if edge is vertical then in addition |c2.y - c1.y| == |c2 - c1|
     *
     * Note the following that this function does not check:
     * 1) if an edge is extended there cannot be another object occupying the
     *    same space. This function does NOT check for this intersection
     *    and will return valid in this case
     */
    //TODO remove
    private boolean isValidEdge(GridObject g1, GridObject g2) {
        Node       n1 = getN1();
        Node       n2 = getN2();
        Coordinate c1 = g1.c();
        Coordinate c2 = g2.c();
        int       ext = getExtensionLength();
        int  longDist = 0;
        int shortDist = 0;
        int     disty = Math.abs(c1.y() - c2.y());
        int     distx = Math.abs(c1.x() - c2.x());

        //object check
        if (!n1.equals(g1.o()) || !n2.equals(g2.o())) { 
            return false; 
        }

        //space check
        if (isVertical) {
            longDist = disty;
            shortDist = distx;
        } else {
            longDist = distx;
            shortDist = disty;
        }

        if (longDist != ext + 1 || shortDist != 0) {
            return false;
        }
        return true;
    }


    //TODO remove
    private boolean isValidEdge(GridObject g1, Grid g) {
        Node n = null;
        Node np = null;
        //find out which node (n) is associated with the coordinate g1
        if (n1.equals(g1.o())) {
            n = n1;
            np = n2;
        } else if (n2.equals(g1.o())) {
            n = n2;
            np = n1;
        } else {
            return false;
        }
        int dir = n.findNeighborDirection(np);
        if (dir == -1) {
            return false;
        }
        int ext = isExtended ? 1 : 0;
        Coordinate coord = g1.c();
        Coordinate c2    = coord.calcRelativeLoc(dir, ext);
        GridObject g2    = g.getGridObject(c2);
        return isValidEdge(g1, g2);
    }
}
