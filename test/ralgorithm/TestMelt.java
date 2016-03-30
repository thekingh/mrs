
package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;
import java.util.ArrayList;

public class TestMelt {

    public static List<State> meltTest(int[][] start, int[][] finish, int dir) {
        Robot r = TestHelper.makeBot(start);
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing Dir: " + dir);
        r.drawModule();
        System.out.println("=====================================");

        List<State> states = meltTestInDirWithBot(r, dir);
        r.drawModule();
        TestHelper.validateOutput(r, f);
        return states;
    }

    public static List<State> meltTestInDirWithBot(Robot r, int dir) {
        Melt m = new Melt(r, dir);
        return m.run();
    }

    public static List<State> easyMeltTest() {
        int[][] start  = {{1,0,0},
                          {1,1,1}};
        int[][] finish = {{0,0,1},
                          {1,1,1}};
        int dir = 1;
        return meltTest(start, finish, dir);
    }
    public static List<State> harderMeltTest() {
        int [][] start  = {{1,1,0},
                           {1,0,0},
                           {1,1,1}};
        int [][] finish = {{0,1,1},
                           {0,0,1},
                           {1,1,1}};
        int dir = 1;
        return meltTest(start, finish, dir);
    }

    public static List<State> zigZagMeltTest() {
        int [][] s  = {{1,0,0,0},
                       {1,1,0,0},
                       {0,1,1,0},
                       {0,0,1,1}};
        int [][] f0 = {{1,1,1,1},
                       {1,1,1,0},
                       {0,0,0,0},
                       {0,0,0,0}};
        int [][] f1 = {{0,0,0,1},
                       {0,0,1,1},
                       {0,0,1,1},
                       {0,0,1,1}};
        int [][] f2 = {{0,0,0,0},
                       {0,0,0,0},
                       {1,1,1,0},
                       {1,1,1,1}};
        int [][] f3 = {{1,0,0,0},
                       {1,1,0,0},
                       {1,1,0,0},
                       {1,1,0,0}};
        int [][][] fs = {f0, f1, f2, f3};

        for (int dir = 0; dir < Direction.NUM_DIR; dir ++) {
            meltTest(s, fs[dir], dir);
        }
        return null;
    }
    public static List<State> makeTunnelTest1() {
        int [][] s = {{1,1,1},
                      {1,1,1},
                      {1,0,1},
                      {1,1,1}};
        int [][] f = {{1,0,1},
                      {1,1,1},
                      {1,1,1},
                      {1,1,1}};
        return meltTest(s, f, 2);
    }

    public static List<State> meltAndReverse() {
        int [][] start  = {{1,1,0},
                           {1,0,0},
                           {1,1,1}};
        int [][] start1  = {{0,1,1},
                           {0,1,0},
                           {1,1,1}};
        int [][] finish = {{0,1,1},
                           {0,0,1},
                           {1,1,1}};
        int dir = 1;
        List<State> forward = meltTest(start, finish, 1);
        List<State> backward = Algorithm.reverse(meltTest(start1, finish, 1));
        forward.addAll(backward);
        return forward;
    }

    public static void main(String[] args) {
/*        easyMeltTest();*/
/*        harderMeltTest();*/
/*        zigZagMeltTest();*/
/*        makeTunnelTest1();*/
        TestHelper.outputStates(meltAndReverse());
    }
}

