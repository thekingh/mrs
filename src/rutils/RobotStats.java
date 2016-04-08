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
        return "Robot has " + numberEdges + " Module Edges in Graph Set";
    }

    public String getUnitsEdgeStat() {
        Graph uGraph = r.getUnitGraph();
        int numberEdges = uGraph.getEdges().size();
        return "Robot has " + numberEdges + " Module Edges in Graph Set";
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
