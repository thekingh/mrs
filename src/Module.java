/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * Senior Capstone Project
 *
 * represents a robot module
 */

package src;

import java.util.Map;
import java.util.HashMap;

public class Module {
	private static int counter = 0;

	private final int id;
	private Module inside;
	private Map<Integer, Edge> connections;

	public Module() {
		id = counter++;
		connections = new HashMap<Integer, Edge>();
	}

	public Module(Map<Integer, Edge> connections) {
		id = counter++;
		this.connections = connections;
	}

	public void setEdge(int direction, Edge edge) {
		connections.put(direction, edge);
	}

	public void getEdge(int direction) {
		connections.get(direction);
	}	

	public Module getInside() {
	    return inside;
	}

	public void setInside(Module inside) {
	    this.inside = inside;
	}

	public boolean hasInside() {
		return (inside == null);
	}

	public int getId() {
		return id;
	}
}








