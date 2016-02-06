package src;

//TODO
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;

/** 
 * State is a snapshot of a robot state
 */
public class State {

    private Graph unitGraph;
    private Graph moduleGraph;

    public State(Robot r) {
        this.unitGraph = r.getUnitGraph();
        this.moduleGraph = r.getModuleGraph();
    }

    public Graph getUnitGraph() {
        return unitGraph;
    }

    public Graph getModuleGraph() {
        return moduleGraph;
    }

    public JSONArray generateJSONRobots() {

        JSONArray robots = new JSONArray();

        Grid grid = unitGraph.toGrid();

        List<GridObject> nodes = grid.getNodes();
        for(GridObject g : nodes) {

            JSONObject rbt = new JSONObject();

            Coordinate c = g.c();

            rbt.put("x", c.x());
            rbt.put("y", c.y());

            Node n = (Node)g.o();
            for(int dir = 0; dir < 4; dir++) {

                Edge e = n.getEdge(dir);
                
                if( e == null) {
                    rbt.put(("ext" + Integer.toString(dir)), -1);
                    rbt.put(("con" + Integer.toString(dir)), -1);
                } else {

                    if(e.isExtended()) {
                        rbt.put(("ext" + Integer.toString(dir)), 1);
                    } else {
                        rbt.put(("ext" + Integer.toString(dir)), 0);
                    }

                    if(e.isConnected()) {
                        rbt.put(("con" + Integer.toString(dir)), 1);
                    } else {
                        rbt.put(("con" + Integer.toString(dir)), 0);
                    }
                }
            } 

            robots.add(rbt);
        }
        return robots;
    }

    public void writeToFile(int stateNum) {
        
        try {
            FileWriter file = new FileWriter("../viz/json_states/state" + stateNum + ".json");
            JSONArray robots = generateJSONRobots();
            file.write(robots.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
