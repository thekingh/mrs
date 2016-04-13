
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestElevator {
    public static void testMove() {
        int [][] s = {{1,1,1},
                      {1,0,1},
                      {1,0,1},
                      {1,0,1},
                      {1,0,1}};
        int [][] f = {{1,0,1},
                      {1,0,1},
                      {1,0,1},
                      {1,0,1},
                      {1,1,1}};

        Robot r = TestHelper.makeBot(s, true);
        int dir = 2;
        Elevator e = new Elevator(r, r.toModuleArray()[1][4], dir, 5, 1);
        TestHelper.runAndDisplayMove(e);
        // TestHelper.validateOutput(r, TestHelper.makeBot(finish));
    }

    public static void main(String[] args) {
        testMove();
    }
}