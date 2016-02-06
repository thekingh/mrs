package src;

import java.util.List;
import java.util.ArrayList;

public class TestParallelMove {
    public static void test1() {
        int [][] m = {{1,1,0,1,1},
                      {1,0,0,0,1},
                      {1,1,1,1,1}};
        boolean[][] moduleBools = TestHelper.convertIntToBool(m);
        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        r.drawUnit();

        Module[][] ms = r.toModuleArray();

        Slide s1 = new Slide(r, ms[1][2], 2, 3);
        Slide s2 = new Slide(r, ms[3][2], 2, 1);

        List<Movement> slides= new ArrayList<Movement>();
        slides.add(s1);
        slides.add(s2);
        ParallelMove p = new ParallelMove(r, slides);
        List<State> allStates = p.pmove();
        System.out.println("number of states = " + allStates.size());
        writeStates(allStates);
    }

    public static void writeStates(List<State> states) {
        for (int i = 0; i < states.size(); i++) {
            states.get(i).writeToFile(i);
        }
    }

    public static void main(String[] args) {
        test1();
/*        test2();*/
    }

}
