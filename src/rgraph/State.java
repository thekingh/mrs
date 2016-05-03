package rgraph;

import rutils.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;

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

    private static final String STATE_PATH_PREFIX = "../data/expanded/";
    private static final String STATE_PATH_EXT    = ".json";

    private JSONArray jsonArr;
    private final Grid g;

    /**Constructs state given a robot
     * @param r     Robot to capture
     */ 
    public State(Robot r) {
        jsonArr = generateJSONRobots(r.getUnitGraph());
        g = r.getUnitGraph().toGrid();
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
            for(int dir = 0; dir < Direction.NUM_DIR; dir++) {

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

    public void printToCommandLine() {
        System.out.println(g.toString());
    }

    /**
     * Writes state to a specified state file.
     * @param stateNum  statefile to write to
     */
    public void writeToFile(String pathPrefix, int stateNum) {
        
        try {
            //TODO remove state
            // FileWriter file = new FileWriter(STATE_PATH_PREFIX + "state" + stateNum + STATE_PATH_EXT);
            FileWriter file = new FileWriter(pathPrefix + "state" + stateNum + STATE_PATH_EXT);
            file.write(jsonArr.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes state to a file in default folder ../viz/states.
     * @param stateNum  statefile to write to
     */
    public void writeToFile(int stateNum) {
        
        try {
            //TODO remove state
            FileWriter file = new FileWriter(STATE_PATH_PREFIX + "state" + stateNum + STATE_PATH_EXT);
            file.write(jsonArr.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void clearStates(String dirName) {
        File dir = new File(dirName);
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    //TODO this path prefix thing... its bad umkay?
    public static void clearStates() {
        //TODO OH GOD HARDCODING LOL
        System.out.println("deleting all files in: ../data/combing/output");
        File dir = new File("../data/combing/output/");
        int count = 0;
        for (File file : dir.listFiles()) {
            file.delete();
            count++;
            System.out.println("files deleted: " + count);
        }
    }
}
