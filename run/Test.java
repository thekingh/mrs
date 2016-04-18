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

public class Test {

    public static void main(String[] args) {
        JSONToInt("input.json");
    }

    public static int[][] JSONToInt(String path) {
        
        ArrayList<Coordinates> coordinates = new ArrayList<Coordinate>();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(path));
            JSONArray ja = (JSONArray)obj;

            for(JSONObject jobj : ja) {
                int x = (int)jobj.get("x");
                int y = (int)jobj.get("y");

                Coordinate c = new Coordinate(x, y);
                coordinates.add(c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("coordinates read in: ");
        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate c = coordinates.get(i);
            System.out.println(">> (" + c.x() + ", " + c.y() + ")");
        }
    }
}
