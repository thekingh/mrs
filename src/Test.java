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

    public static void slideCarryTest1(boolean expanded) {
        System.out.println("------------------------\n"
                         + "sliding module right\n"
                         + "------------------------");
		Module m1 = new Module(expanded);
		Module m2 = new Module(expanded);
		Module m3 = new Module(expanded);
		Module m4 = new Module(expanded);


		Edge e1_2 = m1.addNeighbor(m2, 2, expanded, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, expanded, true);
		Edge e1_4 = m1.addNeighbor(m4, 0, expanded, true);

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
        r.slide(m1, 1, expanded);
    }
    public static void slideExpandedTest1() {
        System.out.println("------------------------\n"
                         + "sliding module right\n"
                         + "------------------------");
		Module m1 = new Module(true);
		Module m2 = new Module(true);
		Module m3 = new Module(true);

		Edge e1_2 = m1.addNeighbor(m2, 2, true, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, true, true);

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
        r.slide(m1, 1, true);
    }



    public static void slideTest1() {
        System.out.println("------------------------\n"
                         + "sliding module right\n"
                         + "------------------------");
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};

        
		Robot r = new Robot(orientArray(moduleBools), false);
        r.drawUnit();
        Module[][] modules = r.toModuleArray();
        int w = modules.length;
        int h = modules[0].length;
        for (int j = h - 1; j >= 0; j--) {
            for (int i = 0; i < w; i++) {
                if (modules[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(modules[i][j]);
                }
            }
            System.out.println();
        }
        Module toSlide = modules[0][1];
        r.slide(toSlide, 1, false);

    }
        

/*		Module m1 = new Module();*/
/*		Module m2 = new Module();*/
/*		Module m3 = new Module();*/
/**/
/*		Edge e1_2 = m1.addNeighbor(m2, 2, false, true);*/
/*		Edge e2_3 = m2.addNeighbor(m3, 1, false, true);*/
/**/
/*		Set<Node> ms = new HashSet<Node>();*/
/*        ms.add(m1);*/
/*        ms.add(m2);*/
/*        ms.add(m3);*/
/**/
/*		Set<Edge> es = new HashSet<Edge>();*/
/*		es.add(e1_2);*/
/*		es.add(e2_3);*/
/*		Graph moduleGraph = new Graph(ms, es);*/
/*		Robot r = new Robot(moduleGraph);*/
/*        //Robot r = Creator.produceLRobot();*/
/*        r.drawUnit();*/
/*        r.slide(m1, 1, false);*/
    public static void slideTest0() {
        System.out.println("------------------------\n"
                         + "sliding module up\n"
                         + "------------------------");
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
        r.slide(m3, 0, false);
        //System.out.println(m3.getConnections());
        //System.out.println(m3.getId());
        //r.drawModule();

        //r.exportToFile("temp.rbt");
        printModuleArray(r);
    }
    //slides in different direction (down)
    public static void slideTest2() {
        System.out.println("------------------------\n"
                         + "sliding module down\n"
                         + "------------------------");
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
		Edge e1_2 = m1.addNeighbor(m2, 2, false, true);
		Edge e2_3 = m1.addNeighbor(m3, 1, false, true);

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
        r.slide(m3, 2, false);
        //System.out.println(m3.getConnections());
        //System.out.println(m3.getId());
        //r.drawModule();

        //r.exportToFile("temp.rbt");
    }

    //slides in different direction (down)
    public static void slideTest3() {
        System.out.println("------------------------\n"
                         + "sliding module left\n"
                         + "------------------------");
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
		Edge e1_2 = m1.addNeighbor(m2, 1, false, true);
		Edge e2_3 = m2.addNeighbor(m3, 2, false, true);

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
        r.slide(m3, 3, false);
        //System.out.println(m3.getConnections());
        //System.out.println(m3.getId());
        //r.drawModule();

        //r.exportToFile("temp.rbt");
    }

    private static void printModuleArray(Robot r) {
        System.out.println("Module Array");
        Module[][] arr = r.toModuleArray();
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr[i].length - 1; j >= 0; j--) {
                System.out.print(arr[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

	public static void main(String[] args) {
        //printTest();
/*        slideCarryTest1(false);*/
//        slideCarryTest1(true);
        slideTest0();
	}
}
