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
		Module m3 = new Module(2);
		Edge e1_2 = m1.addNeighbor(m2, 2, true, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, true, true);

		Map<Integer, Node> ms = new HashMap<Integer, Node>();
		ms.put(m1.getId(), m1);
		ms.put(m2.getId(), m2);
		ms.put(m3.getId(), m3);

		Map<Integer, Edge> es = new HashMap<Integer, Edge>();
		es.put(0, e1_2);
		es.put(1, e2_3);

		Graph g = new Graph(ms, es);
		Grid c = g.toGrid();
		System.out.println(c);

	}



}



