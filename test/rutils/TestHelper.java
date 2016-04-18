package rutils;

import rgraph.*;
import ralgorithm.*;
import java.util.List;
import java.util.ArrayList;

public final class TestHelper {
    public static void printRobot(Robot r) {
        Module[][] modules = r.toModuleArray();
        int w = modules.length;
        int h = modules[0].length;
        for (int j = h - 1; j >= 0; j--) {
            for (int i = 0; i < w; i++) {
                if (modules[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(modules[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static void printStatesToCommandLine(List<State> states) {
        for (State s: states) {
            delay(500);
            s.printToCommandLine();
        }
    }

    private static void delay(int millis) {
        try {
            Thread.sleep(millis); 
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void runAndDisplayMove(Movement m) {
        Robot r = m.getRobot();
        while (!m.reachedEnd()) {
            m.step();
            assert r.isConnected();
            r.drawUnit();
        }
        m.finalize();
        r.drawModule();
    }

    public static void runAndDisplayMoveForSteps(Movement m, int s) {
        Robot r = m.getRobot();
        for (int i = 0; i < s; i++) {

            m.step();
            assert r.isConnected();
            r.drawUnit();
        }
        m.finalize();
        r.drawModule();
    }

    public static void runMove(Movement m) {
        while (!m.reachedEnd()) {
            m.step();
            assert m.getRobot().isConnected();
        }
        m.finalize();
    }

    public static void runMoveForSteps(Movement m, int s) {
        for (int i = 0; i < s; i++) {
            m.step();
            assert m.getRobot().isConnected();
        }
        m.finalize();
    }

    public static Robot makeBot(int[][] in, boolean expanded) {
        return new Robot(orientArray(in), expanded);
    }

    public static Robot makeBot(int[][] in) {
        return makeBot(in, false);
    }

    public static void testConnectedness(Robot r, boolean connected) {
        assert r.isConnected() == connected;
        System.out.println("Connectedness Test Passed");
    }

    public static boolean verifyShape(Robot r, Robot s) {
        return r.equals(s);
    }

    public static void validateOutput(Robot r, Robot s) {
        if (verifyShape(r, s)) {
            System.out.println("OK");
        } else {
            throw new RuntimeException("NOT OK: Robot shapes do not match");
        }
    }

    private static boolean[][] orientArray(boolean[][] in) {
        return mirrorArray(invertArray(in));
    }
    private static boolean[][] orientArray(int[][] in) {
        return mirrorArray(invertArray(convertIntToBool(in)));
    }

    private static boolean[][] invertArray(boolean[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[h][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[j][i] = in[i][j];
            }
        }
        return out;
    }
    private static boolean[][] mirrorArray(boolean[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[i][j] = in[i][h - j - 1];
            }
        }
        return out;
    }

    private static boolean[][] convertIntToBool(int[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[i][j] = (in[i][j] == 1);
            }
        }
        return out;
    }

    /**
     * Clears states then writes new states to viz readable file
     */
    public static void outputStates(List<State> states) {
        //clearStates();
        for (int i = 0; i < states.size(); i++) {
            System.out.println(i);
            states.get(i).writeToFile(i);
        }
    }

    public static void clearStates() {
        State.clearStates();
    }
}
