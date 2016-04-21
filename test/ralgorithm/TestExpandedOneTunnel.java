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

//        TestHelper.runAndDisplayMove(p);
        List<State> states = BotBuilder.runMove(p);

        System.out.println("Draw Result");
        r.drawModule();
        r.drawUnit();
        System.out.println("Draw Expected Final");
        f.drawModule();
        f.drawUnit();
        TestHelper.validateOutput(r, f);
        return states;
    }

    public static void testSimpleTwoDR() {
        int[][] s = {{1},
                     {1}};
        int[][] f = {{1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][1], 2, 1);

        TestHelper.outputStates(oneTunnelTest(r, f, p0));

    }

    public static void testSimpleTwoDL() {
        int[][] s = {{1},
                     {1}};
        int[][] f = {{1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][1], 2, 3);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleThreeDR() {
        int[][] s = {{0,1},
                     {1,1}};
        int[][] f = {{1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[1][1], 2, 1);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleThreeDRCoords() {
        int[][] s = {{0,1},
                     {1,1}};
        int[][] f = {{1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoords(r, new Coordinate(1,1), new Coordinate(2,0));

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleThreeRightDR() {
        int[][] s = {{1,0},
                     {1,1}};
        int[][] f = {{1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][1], 2, 1);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleFourRightDR() {
        int[][] s  = {{1,0,0},
                      {1,1,1}};       
        int[][] f = {{1,1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][1], 2, 1);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleThreeBottomDR() {
        int[][] s = {{1},
                     {1},
                     {1}};
        int[][] f = {{0,1,1},
                     {0,1,0}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[0][2], 2, 1);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleFourDR() {
        int[][] s = {{1,1},
                     {1,1}};
        int[][] f = {{1,0,0},
                     {1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[1][1], 2, 1);

        oneTunnelTest(r, f, p0);
    }

    public static void testHardEightDR() {
        int[][] s = {{1,0,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,1}};
        int[][] f = {{1,0,0,0,0,0},
                     {1,0,0,0,0,0},
                     {1,1,1,1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        // ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[1][1], 2, 1);
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(1,1), new Coordinate(2,0));

        oneTunnelTest(r, f, p0);
    }

    public static void testTwoConsecutive() {
        int[][] s = {{1,0,0,0},
                     {1,1,1,0},
                     {1,1,1,1}};
        int[][] f1 = {{1,0,0,0,0},
                      {1,1,0,0,0},
                      {1,1,1,1,1}};
        int[][] f2 = {{1,0,0,0,0,0},
                      {1,0,0,0,0,0},
                      {1,1,1,1,1,1}};
        Robot r = TestHelper.makeBot(s);

        ExpandAll exp = new ExpandAll(r);
        TestHelper.runAndDisplayMove(exp);

        // Module[][] ms0 = r.toModuleArray();
        // ExpandedOneTunnel p0 = new ExpandedOneTunnel(r, ms0[1][1], 2, 1);
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(2,1), new Coordinate(3,0));
        oneTunnelTest(r, f1, p0);

        System.out.println("Connecting");
        ConnectAll conn = new ConnectAll(r, true);
        TestHelper.runAndDisplayMove(conn);

        ExpandedOneTunnel p1 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(1,1), new Coordinate(2,0));
        oneTunnelTest(r, f2, p1);
    }

    public static void testHardTwoDown() {
        int[][] s = {{1,1,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,1}};
        int[][] f = {{1,0,0,0,0,0},
                     {1,1,0,0,0,0},
                     {1,1,1,1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(1,2), new Coordinate(2,0));

        oneTunnelTest(r, f, p0);
    }

    public static void testHardTwoDownTwoOver() {
        int[][] s = {{1,1,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,0},
                     {1,1,1,1,1}};
        int[][] f = {{1,0,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,1},
                     {1,1,1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(1,2), new Coordinate(4,1));

        oneTunnelTest(r, f, p0);
    }

    public static void testHardBroken() {
        // should break
        int[][] s = {{1,1,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,0},
                     {1,1,1,1,1}};
        int[][] f = {{1,0,0,0,0},
                     {1,1,0,0,0},
                     {1,1,1,1,1},
                     {1,1,1,1,1}};
        Robot r = TestHelper.makeBot(s, true);

        Module[][] ms0 = r.toModuleArray();
        ExpandedOneTunnel p0 = ExpandedOneTunnel.initFromCoordsWithDir(r, 2,
            new Coordinate(1,2), new Coordinate(2,0));

        oneTunnelTest(r, f, p0);
    }


    public static void main(String[] args) {
         testSimpleTwoDR();
        // testSimpleTwoDL();
        // testSimpleThreeDR();
        // testSimpleThreeDRCoords();
        // testSimpleThreeRightDR();
        // testSimpleFourRightDR();
        // testSimpleThreeBottomDR();
        // testSimpleFourDR();
        // testHardEightDR();
        // testTwoConsecutive();
        // testHardTwoDown();
        // testHardTwoDownTwoOver();
    }

}
