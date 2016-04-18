package ralgorithm;

import rutils.*;
import rgraph.*;

import java.util.List;
import java.util.ArrayList;

public class TestParallelMoveJSON {

    public static void pMoveTest(Robot r, int[][] finish, List<Movement> moves) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Testing " + moves.size() + " moves");
        r.drawModule();
        System.out.println("=====================================");

        ParallelMove p = new ParallelMove(r, moves);
        List<State> allStates = p.pmove();
        r.drawModule();
/*        TestHelper.validateOutput(r, f);*/
    }

    public static void test1() {
/*        int [][] s = {{1,1,0,1,1},*/
/*                      {1,0,0,0,1},*/
/*                      {1,1,1,1,1}};*/
        int [][] s = TestHelper.generateIntFromJSON();

        int [][] f  = {{1,0,0,0},
                       {1,1,0,0},
                       {1,0,0,1},
                       {1,1,1,1}};


        Robot r = TestHelper.makeBot(s);

        Module[][] ms = r.toModuleArray();

       System.out.println("module array:"); 
        for(int i = 0; i < ms.length; i++) {
            for(int j = 0; j < ms[0].length; j++) {
                if(ms[i][j] != null) {
                    System.out.print("X");
                } else {
                    System.out.print("-");
                }
            }
                System.out.println();
        }

        if(ms[1][3] == null) {
            System.out.println("br is null");
        }

        if(ms[2][1] == null) {
            System.out.println("tl is null");
        }

        Slide s1 = new Slide(r, ms[2][1], 3, 0);
        Slide s2 = new Slide(r, ms[1][3], 0, 3);

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
    }

}
