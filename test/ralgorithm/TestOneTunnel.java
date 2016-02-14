package ralgorithm;

import rutils.*;
import rgraph.*;

public class TestOneTunnel {

    public static void oneTunnelTest(Robot r, int[][] finish, OneTunnel p) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("1-Tunnel");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runAndDisplayMoveForSteps(p, 30);

        r.drawModule();
        TestHelper.validateOutput(r, f);
    }

    public static void testSimpleDR() {
        int[][] s = {{1,1},
                     {1,0}};
        int[][] f = {{0,1},
                     {1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        OneTunnel p0 = new OneTunnel(r, ms0[0][1], 2, 1);

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

        oneTunnelTest(r, f, p0);
    }

    // TODO: add new directions
    public static void test3() {
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

        oneTunnelTest(r, f, p0);
    }

    public static void main(String[] args) {
        // testSimpleDR();
        // testSimpleDL();
        // testSimpleUR();
        // testSimpleUL();
        // test3();
        testCoordinateConstructor();
    }

}
