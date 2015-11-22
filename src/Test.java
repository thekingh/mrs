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

		Module m1 = new Module(4);
		Module m2 = new Module(4);
		Module m3 = new Module(4);
		Edge e1_2 = m1.addNeighbor(m2, 0, true, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, true, true);

		Map<Integer, Node> ms = new HashMap<Integer, Node>();
		ms.put(m1.getId(), m1);
		ms.put(m2.getId(), m2);
		ms.put(m3.getId(), m3);

		Set<Edge> es = new HashSet<Edge>();
		es.add(e1_2);
		es.add(e2_3);

		Graph moduleGraph = new Graph(ms, es);
		Grid moduleGrid = moduleGraph.toGrid();
		System.out.println(moduleGrid);

		Robot r = new Robot(moduleGraph);

		Graph unitGraph = r.getUnitGraph();
		Grid unitGrid = unitGraph.toGrid();
		System.out.println(unitGrid);
	

	}



}



