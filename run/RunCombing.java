
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class RunCombing {

    public static List<State> combTest(Robot s, Robot t) {

        System.out.println("=====================================");
        System.out.println("Testing");
        s.drawModule();
        System.out.println("=====================================");

        Combing c = new Combing(s, t);
        List<State> states = c.run();
        System.out.println(states.size());
/*        BotBuilder.printStatesToCommandLine(states);*/
        return states;
    }

    public static void JSONCombHelper(String output_path, String start_path, String end_path) {

        BotBuilder.outputStates(output_path, JSONComb(start_path, end_path));
    }

    public static List<State> JSONComb(String start_path, String end_path) {
        Robot s  = BotBuilder.makeBot(start_path);
        Robot t  = BotBuilder.makeBot(end_path);

        return combTest(s, t);
    }

    public static List<State> easyCombToLine() {
        int[][] start  = {{1,1,1,1},
                          {1,0,0,0},
                          {1,1,1,0}};
        int[][] finish = {{1,1,1,1,1},
                          {1,0,0,1,1}};
        return combTest(BotBuilder.makeBot(start), BotBuilder.makeBot(finish));
    }

    public static void main(String[] args) {
/*        BotBuilder.outputStates("../data/combing/", easyCombToLine());*/
        BotBuilder.outputStates("../data/combing/output/",
                                JSONComb("../data/combing/input/start.json",
                                         "../data/combing/input/start.json"));
    }
}
