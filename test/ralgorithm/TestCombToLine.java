
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestCombToLine {

    public static List<State> combToLineTest(int[][] start, int[][] finish, int dir) {
        Robot r = TestHelper.makeBot(start, true);
        Robot f = TestHelper.makeBot(finish, true);

        System.out.println("=====================================");
        System.out.println("Testing Dir: " + dir);
        r.drawModule();
        System.out.println("=====================================");

        CombToLine cl = new CombToLine(r, dir);
        List<State> states = cl.run();
        r.drawModule();
        TestHelper.validateOutput(r, f);
        return states;
    }

    public static void easyCombToLine() {
        int[][] start  = {{1,0,0},
                          {1,1,1}};
        int[][] finish = {{1,1,1,1}};
        int dir = 2;
        combToLineTest(start, finish, dir);
    }

    

    public static void main(String[] args) {
        easyCombToLine();
    }
}

