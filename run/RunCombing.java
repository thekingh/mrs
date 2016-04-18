
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class RunCombing {

    public static List<State> combTest(int[][] start, int[][] finish) {

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
}
