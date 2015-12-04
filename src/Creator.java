/**
 * Creates simple module graphs that can then be used for more complex shapes
 */

package src;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Creator {

    /**
     * produce robot.
     * M
     * MM
     */
    public static Robot produceLRobot() {
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
        return r;
    }

    public static Robot produceBridgeRobot() {
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
        Module m4 = new Module();
		Edge e1_2 = m1.addNeighbor(m2, 0, true, true);
		Edge e2_3 = m2.addNeighbor(m3, 1, true, true);
		Edge e3_4 = m3.addNeighbor(m4, 2, true, true);

		Set<Node> ms = new HashSet<Node>();
        ms.add(m1);
        ms.add(m2);
        ms.add(m3);
        ms.add(m4);

		Set<Edge> es = new HashSet<Edge>();
		es.add(e1_2);
		es.add(e2_3);
		es.add(e3_4);

		Graph moduleGraph = new Graph(ms, es);
		Robot r = new Robot(moduleGraph);
        return r;
    }

    public static Graph singleModule() {
        Module m = new Module();
		Set<Node> ms = new HashSet<Node>();
		ms.add(m);
		Set<Edge> es = new HashSet<Edge>();

		Graph moduleGraph = new Graph(ms, es);
        return moduleGraph;
    }
}
