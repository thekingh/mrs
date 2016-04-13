
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestElevator {
    public static void testElevatorDown(int k, int l) {
        int dir = 2;

        int[][] s = new int[k][l + 2];
        int[][] f = new int[k][l + 2];

        for (int j = 0; j < k; j++) {
            for (int i = 0; i < l+2; i++) {
                if (j == 0 && i != 0 && i != l+1) {
                    s[j][i] = 1;
                } else if (i == 0 || i == l+1) {
                    s[j][i] = 1;
                } else {
                    s[j][i] = 0;
                }
            }
        }
        for (int j = 0; j < k; j++) {
            for (int i = 0; i < l+2; i++) {
                if (j == k-1 && i != 0 && i != l+1) {
                    f[j][i] = 1;
                } else if (i == 0 || i == l+1) {
                    f[j][i] = 1;
                } else {
                    f[j][i] = 0;
                }
            }
        }

        Robot r = TestHelper.makeBot(s, true);
        r.drawUnit();

        Elevator e = new Elevator(r, r.toModuleArray()[1][k-1], dir, k, l);
        TestHelper.runAndDisplayMove(e);
        TestHelper.validateOutput(r, TestHelper.makeBot(f));
    }

    public static void main(String[] args) {
        // testElevatorDown(5, 1);
        // testElevatorDown(5, 3);
        // testElevatorDown(9, 4);
        // testElevatorDown(6, 1);
        testElevatorDown(10, 4);
    }
}