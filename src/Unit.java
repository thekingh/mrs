/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * robot unit with 4 arms
 */

package src;

public class Unit extends Node {

	public Unit() {
		super();
	}

    public void connect(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsConnected(true);
        }
    }

    public void disconnect(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsConnected(false);
        }
    }

    public void extend(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsExtended(true);
        }
    }

    public void contract(int dir) {
        Edge e;
        if (hasEdge(dir)) {
            e = getEdge(dir);
            e.setIsExtended(true);
        }
    }

	public String toString() {
		return "o";
	}
}
