package src;

public class Edge {
	private static int counter = 0;
	private final int id;
	private boolean isExtended;
	private boolean isConnected;
	private final boolean isVertical;
	private final Node n1;
	private final Node n2;


	// NEEDWORK: change ordering of args?
	public Edge(boolean isExtended, boolean isConnected, boolean isVertical, Node n1, Node n2) {
		id = counter++;
		this.isExtended = isExtended;
		this.isConnected = isConnected;
		this.isVertical = isVertical;
		this.n1 = n1;
		this.n2 = n2;
	}

	public boolean isExtended() {
	    return isExtended;
	}
	public void setIsExtended(boolean isExtended) {
	    this.isExtended = isExtended;
	}

	public boolean isConnected() {
	    return isConnected;
	}
	public void setIsConnected(boolean isConnected) {
	    this.isConnected = isConnected;
	}

	public boolean isVertical() {
	    return isVertical;
	}

	public Node getN1() {
	    return n1;
	}

	public Node getN2() {
	    return n2;
	}

	public Node getOpposite(Node n) {
		if (n.equals(n1)) {
			return n2;
		} else {
			return n1;
		}
	}

	public String toString() {
		if (isVertical) {
			return "|";
		} else {
			return "-";
		}
	}

}