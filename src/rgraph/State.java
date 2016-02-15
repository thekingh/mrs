package rgraph;

import rutils.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/** 
 * State is a snapshot of a robot state.
 * <p>
 * State is used for the visualization of a robot, by storing state objects
 * we can replay algorithms.
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 */
public class State {

    private JSONArray jsonArr;

    /**Constructs state given a robot
     * @param r     Robot to capture
     */ 
    public State(Robot r) {
        jsonArr = generateJSONRobots(r.getUnitGraph());
    }

    /**
     * Generates json object representation of a robot.
     * @param unitGraph unitgraph representation to capture in json
     * @return          jsonarray containing unitgraph information
     */
    public JSONArray generateJSONRobots(Graph unitGraph) {

        JSONArray robots = new JSONArray();

        Grid grid = unitGraph.toGrid();

        List<GridObject> nodes = grid.getNodes();
        for(GridObject g : nodes) {

            JSONObject rbt = new JSONObject();

            Coordinate c = g.c();

            rbt.put("x", c.x());
            rbt.put("y", c.y());

            Node n = (Node)g.o();
            for(int dir = 0; dir < Direction.MAX_DIR; dir++) {

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

    /**
     * Writes state to a specified state file.
     * @param stateNum  statefile to write to
     */
    public void writeToFile(int stateNum) {
        
        try {
            FileWriter file = new FileWriter("../viz/json_states/state" + stateNum + ".json");
            file.write(jsonArr.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
