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
import java.util.ArrayList;
import java.util.HashSet;

public class Test {

    public static void printTest() {
        Robot r = Creator.produceLRobot();
        Graph mg = r.getModuleGraph();
        System.out.println(mg.toGrid());

		Graph unitGraph = r.getUnitGraph();
		Grid unitGrid = unitGraph.toGrid();
		System.out.println(unitGrid);
    }

    public static void slideTest() {
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
		Edge e1_2 = m1.addNeighbor(m2, 2, false, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, false, true);

		Set<Node> ms = new HashSet<Node>();
        ms.add(m1);
        ms.add(m2);
        ms.add(m3);

		Set<Edge> es = new HashSet<Edge>();
		es.add(e1_2);
		es.add(e2_3);
		Graph moduleGraph = new Graph(ms, es);
		Robot r = new Robot(moduleGraph);
        //Robot r = Creator.produceLRobot();
        r.drawUnit();
        r.slide(m1, 1);
    }
    //slides in different direction (up)
    public static void slideTest2() {
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
		Edge e1_2 = m1.addNeighbor(m2, 2, false, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, false, true);

		Set<Node> ms = new HashSet<Node>();
        ms.add(m1);
        ms.add(m2);
        ms.add(m3);

		Set<Edge> es = new HashSet<Edge>();
		es.add(e1_2);
		es.add(e2_3);
		Graph moduleGraph = new Graph(ms, es);
		Robot r = new Robot(moduleGraph);
        //Robot r = Creator.produceLRobot();
        r.drawModule();
        r.drawUnit();
        r.slide(m3, 0);
        System.out.println(m3.getConnections());
        System.out.println(m3.getId());
        r.drawModule();

        r.exportToFile("temp.rbt");
    }

	public static void main(String[] args) {
        //printTest();
        //slideTest();
        slideTest2();

	}



}



