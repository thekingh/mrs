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

    }

	public static void main(String[] args) {
        printTest();

	}



}



