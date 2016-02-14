package ralgorithm;

import rutils.*;
import rgraph.*;
import tst.*;

import java.util.List;
import java.util.ArrayList;

public class TestParallelMove {

    public static void pMoveTest(Robot r, int[][] finish, List<Movement> moves) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing " + moves.size() + " moves");
        r.drawModule();
        System.out.println("=====================================");

        ParallelMove p = new ParallelMove(r, moves);
        List<State> allStates = p.pmove();
        r.drawModule();
        TestHelper.validateOutput(r, f);
    }

    public static void test1() {
        int [][] s = {{1,1,0,1,1},
                      {1,0,0,0,1},
                      {1,1,1,1,1}};
        int [][] f = {{1,0,0,0,1},
                      {1,1,0,1,1},
                      {1,1,1,1,1}};

        Robot r = TestHelper.makeBot(s);

        Module[][] ms = r.toModuleArray();

        Slide s1 = new Slide(r, ms[1][2], 2, 3);
        Slide s2 = new Slide(r, ms[3][2], 2, 1);

        List<Movement> slides= new ArrayList<Movement>();
        slides.add(s1);
        slides.add(s2);

        pMoveTest(r, f, slides);
    }

    public static void test2() {
        int [][] s = {{1,1,1},
                      {1,0,1},
                      {1,1,1}};
        int [][] f = {{1,0,1},
                      {1,1,1},
                      {1,1,1}};

        Robot r = TestHelper.makeBot(s);

        Module[][] ms = r.toModuleArray();

        Slide s1 = new Slide(r, ms[1][2], 2, 3);
        Slide s2 = new Slide(r, ms[1][2], 2, 1);

        List<Movement> slides= new ArrayList<Movement>();
        slides.add(s1);
        slides.add(s2);

        pMoveTest(r, f, slides);
    }

    public static void writeStates(List<State> states) {
        for (int i = 0; i < states.size(); i++) {
            states.get(i).writeToFile(i);
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

}
