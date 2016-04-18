
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class RunStaircase {
    public static List<State> testMove(int w, int h) {
        int[][] start = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                start[i][j] = 1;
            }
        }

        int[][] finish = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                finish[i][j] = 1;
            }
        }

        Robot r = BotBuilder.makeBot(start);
        Staircase s = new Staircase(r, r.toModuleArray()[0][0], h, w);
        List<State> states = BotBuilder.runMove(s);
        BotBuilder.printStatesToCommandLine(states);
        return states;
    }

    public static void main(String[] args) {
        BotBuilder.outputStates("../data/staircase/", testMove(15, 25));
    }
}
