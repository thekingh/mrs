
package ralgorithm;

import rutils.*;
import rgraph.*;

public class TestCombToLine {

    public static void combToLineTest(int[][] start, int[][] finish, int dir) {
        Robot r = TestHelper.makeBot(start);
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing Dir: " + dir);
        r.drawModule();
        System.out.println("=====================================");

        CombToLine cl = new CombToLine(r, dir);
        cl.run();
        r.drawModule();
        TestHelper.validateOutput(r, f);
    }

    public static void easyCombToLine() {
        int[][] start  = {{1,1,0},
                          {1,1,1}};
        int[][] finish = {{1,1,1,1,1}};
        int dir = 2;
        combToLineTest(start, finish, dir);
    }

    

    public static void main(String[] args) {
        easyCombToLine();
    }
}

