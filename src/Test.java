/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * Test.java
 *
 * test main class
 */

package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) {
		System.out.println("Yo.");

		Module m1 = new Module(2);
		Module m2 = new Module(2);
		Edge e1 = new Edge(m1, m2, true, true, true);

		Map<Integer, Node> ms = new HashMap<Integer, Node>();
		ms.put(m1.getId(), m1);
		ms.put(m2.getId(), m2);

		Map<Integer, Edge> es = new HashMap<Integer, Edge>();
		es.put(0, e1);

		m1.putEdge(0, e1);
		m2.putEdge(2, e1);

		Graph g = new Graph(ms, es);
		Grid c = g.toGrid();
		System.out.println(c);

	}



}



