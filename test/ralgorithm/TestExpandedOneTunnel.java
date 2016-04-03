package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestExpandedOneTunnel {

    public static List<State> oneTunnelTest(Robot r, int[][] finish, ExpandedOneTunnel p) {
        Robot f = TestHelper.makeBot(finish, true);

        System.out.println("=====================================");
        System.out.println("1-Tunnel");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runAndDisplayMove(p);

        System.out.println("Draw Result");
        r.drawModule();
        r.drawUnit();
        System.out.println("Draw Expected Final");
        f.drawModule();
        f.drawUnit();
        TestHelper.validateOutput(r, f);
        return null;
    }


    public static void testSimpleTwoBlocks() {
        int[][] s = {{1},
                     {1}};
        int[][] f = {{1,1}};
        Robot r = TestHelper.makeBot(s, true);
        // r.expandAll();

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][1], 2, 1);

        oneTunnelTest(r, f, p0);
    }


    public static void main(String[] args) {
        testSimpleTwoBlocks();
    }

}
