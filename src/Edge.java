package src;

public class Edge {
	private static int counter = 0;
	private final int id;
	private boolean isExtended;
	private boolean isConnected;
	private boolean isVertical;
	private Node n1;
	private Node n2;

	public Edge(boolean isExtended, boolean isConnected, boolean isVertical, Node n1, Node n2) {
		id = counter++;
		this.isExtended = isExtended;
		this.isConnected = isConnected;
		this.isVertical = isVertical;
		this.n1 = n1;
		this.n2 = n2;
	}

	//TODO write getters and setters
	
}