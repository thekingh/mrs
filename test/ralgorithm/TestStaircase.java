
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestStaircase {
    public static void testMove(int w, int h) {
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

        Robot r = TestHelper.makeBot(start);
        Staircase s = new Staircase(r, r.toModuleArray()[0][0], h, w);
        TestHelper.runAndDisplayMoveForSteps(s, 70);
        TestHelper.validateOutput(r, TestHelper.makeBot(finish));
    }

    public static void main(String[] args) {
        // test move w/ w < h
        // testMove(3, 5);
        // testMove(4, 4);
        // testMove(5, 9);
        // testMove(10, 16);
        testMove(15, 25);
    }
}
