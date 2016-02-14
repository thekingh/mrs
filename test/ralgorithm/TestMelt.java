
package ralgorithm;

import rutils.*;
import rgraph.*;
import tst.*;

public class TestMelt {

    public static void meltTest(int[][] start, int[][] finish, int dir) {
        Robot r = TestHelper.makeBot(start);
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing Dir: " + dir);
        r.drawModule();
        System.out.println("=====================================");

        meltTestInDirWithBot(r, dir);
        r.drawModule();
        TestHelper.validateOutput(r, f);
    }

    public static void meltTestInDirWithBot(Robot r, int dir) {
        Melt m = new Melt(r, dir);
        m.run();
    }

    public static void easyMeltTest() {
        int[][] start  = {{1,0,0},
                          {1,1,1}};
        int[][] finish = {{0,0,1},
                          {1,1,1}};
        int dir = 1;
        meltTest(start, finish, dir);
    }
    public static void harderMeltTest() {
        int [][] start  = {{1,1,0},
                           {1,0,0},
                           {1,1,1}};
        int [][] finish = {{0,1,1},
                           {0,0,1},
                           {1,1,1}};
        int dir = 1;
        meltTest(start, finish, dir);
    }

    public static void zigZagMeltTest() {
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

        for (int dir = 0; dir < Direction.MAX_DIR; dir ++) {
            meltTest(s, fs[dir], dir);
        }

    }

    

    public static void main(String[] args) {
/*        easyMeltTest();*/
/*        harderMeltTest();*/
        zigZagMeltTest();

    }
}

