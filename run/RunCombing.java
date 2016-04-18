
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

/*String inputStartPath = "../data/combing/start.json";*/
/*String inputEndPath   = "../data/combing/end.json";*/

public class RunCombing {

    public static List<State> combTest(int[][] start, int[][] finish) {

/*        int[][] start_json = JSONToInt(inputStartPath);*/
/*        int[][] end_json   = JSONToInt(inputEndPath);*/
        
        Robot s = BotBuilder.makeBot(start);
        Robot t = BotBuilder.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing");
        s.drawModule();
        System.out.println("=====================================");

        Combing c = new Combing(s, t);
        List<State> states = c.run();
        System.out.println(states.size());
        BotBuilder.printStatesToCommandLine(states);
        return states;
    }

    public static List<State> easyCombToLine() {
        int[][] start  = {{1,1,1,1},
                          {1,0,0,0},
                          {1,1,1,0}};
        int[][] finish = {{1,1,1,1,1},
                          {1,0,0,1,1}};
        return combTest(start, finish);
    }

    public static void main(String[] args) {
        BotBuilder.outputStates("../data/combing/", easyCombToLine());
    }

/*    public static int[][] JSONToInt(String path) {*/
/*        */
/*        ArrayList<Coordinates> coordinates = new ArrayList<Coordinate>();*/
/*        JSONParser parser = new JSONParser();*/
/**/
/*        try {*/
/*            Object obj = parser.parse(new FileReader(path));*/
/*            JSONArray ja = (JSONArray)obj;*/
/**/
/*            for(JSONObject jobj : ja) {*/
/*                int x = (int)jobj.get("x");*/
/*                int y = (int)jobj.get("y");*/
/**/
/*                Coordinate c = new Coordinate(x, y);*/
/*                coordinates.add(c);*/
/*            }*/
/**/
/*        } catch (FileNotFoundException e) {*/
/*            e.printStackTrace();*/
/*        } catch (IOException e) {*/
/*            e.printStackTrace();*/
/*        } catch (ParseException e) {*/
/*            e.printStackTrace();*/
/*        }*/
/*    }*/
}
