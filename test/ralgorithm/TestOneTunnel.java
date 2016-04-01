package ralgorithm;

import rutils.*;
import rgraph.*;
import java.util.List;

public class TestOneTunnel {

    public static List<State> oneTunnelTest(Robot r, int[][] finish, OneTunnel p) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("1-Tunnel");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runAndDisplayMove(p);

        r.drawModule();
        TestHelper.validateOutput(r, f);
        return null;
    }
    
    public static void testUnionUse(OneTunnel m, boolean expectedUnion) {
        assert(m.isUsingUnion() == expectedUnion);
    }

    public static void testSimpleDR() {
        int[][] s = {{1,1},
                     {1,0}};
        int[][] f = {{0,1},
                     {1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[0][1], 2, 1);
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }
     public static void testSimpleTwoBlocks() {
         int[][] s = {{1},
                      {1}};
         int[][] f = {{1,1}};
         Robot r = TestHelper.makeBot(s);

         Module[][] ms0 = r.toModuleArray();
         OneTunnel p0 = new OneTunnel(r, ms0[0][1], 2, 1);
        testUnionUse(p0, false);

         oneTunnelTest(r, f, p0);
     }

    public static void testSimpleDL() {
        int[][] s = {{1,1},
                     {0,1}};
        int[][] f = {{1,0},
                     {1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[1][1], 2, 3);
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleUR() {
        int[][] s = {{1,0},
                     {1,1}};
        int[][] f = {{1,1},
                     {0,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[0][0], 0, 1);
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }

    public static void testSimpleUL() {
        int[][] s = {{0,1},
                     {1,1}};
        int[][] f = {{1,1},
                     {1,0}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[1][0], 0, 3);
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }

    // TODO: add new directions
    public static void testComplexDR() {
        // What should this look like??
        int[][] s = {{1,1,0},
                     {1,1,0},
                     {0,1,1}};
        int[][] f = {{1,0,0},
                     {1,1,1},
                     {0,1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[1][2], 2, 1);
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }

    public static void testCoordinateConstructor() {
        int[][] s = {{1,0},
                     {1,1},
                     {1,1}};
        int[][] f = {{1,1,0},
                     {1,1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = OneTunnel.initFromCoords(r, new Coordinate(0,2), new Coordinate(2,0));
        testUnionUse(p0, true);

        oneTunnelTest(r, f, p0);
    }

    public static void main(String[] args) {
        testSimpleTwoBlocks();
        // testSimpleDL();
        testSimpleDR();
        // testSimpleUR();
        // testSimpleUL();
        testComplexDR();
        // test3();
        //testCoordinateConstructor();
    }

}
