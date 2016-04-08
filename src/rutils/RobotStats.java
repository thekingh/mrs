package rutils;
import rgraph.*;

/**
 * Robot Stats calculates and displays on the command line various statistics
 * about a Robot object.
 *
 * This can be useful for debugging complicated robot objects.
 */

public class RobotStats {
    private Robot r;

    public RobotStats(Robot r) {
        this.r = r;
    }

    public String getIsExpandedStat() {
        if (r.isExpanded()) {
            return "Robot is Expanded";
        } else {
            return "Robot is not Expanded";
        }
    }

    public String getModuleEdgeStat() {
        Graph mGraph = r.getModuleGraph();
        int numberEdges = mGraph.getEdges().size();
        int expandEdges = 0;
        for (Edge e : mGraph.getEdges()) {
            if (e.isExtended()) {
                expandEdges += 1;
            }
        }
        return "Robot has " + numberEdges + " Module Edges in Graph Set\n" +
               "Robot has " + expandEdges + " Expanded Modules Edges";
    }

    public String getUnitsEdgeStat() {
        Graph uGraph = r.getUnitGraph();
        int numberEdges = uGraph.getEdges().size();
        int expandEdges = 0;
        for (Edge e : uGraph.getEdges()) {
            if (e.isExtended()) {
                expandEdges += 1;
            }
        }
        return "Robot has " + numberEdges + " Unit Edges in Graph Set\n" + 
               "Robot has " + expandEdges + " Expanded Unit Edges";
    }

    public String getAllStats() {
        String s = "";
        s += getIsExpandedStat() + '\n';
        s += getModuleEdgeStat() + '\n';
        s += getUnitsEdgeStat() + '\n';
        return s;
    }

    public static String getAll(Robot r) {
        RobotStats tmp = new RobotStats(r);
        return tmp.getAllStats();
    }
}
