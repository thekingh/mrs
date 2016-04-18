package rutils;

import rgraph.*;
import ralgorithm.*;
import java.util.List;
import java.util.ArrayList;

public final class BotBuilder {
    public static List<State> runMove(Movement m) {
        List<State> states = new ArrayList<State>();
        while (!m.reachedEnd()) {
            m.step();
            assert m.getRobot().isConnected();
            states.add(new State(m.getRobot()));
        }
        m.finalizeMove();
        states.add(new State(m.getRobot()));

        return states;
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
        clearStates();
        for (int i = 0; i < states.size(); i++) {
            System.out.println(i);
            states.get(i).writeToFile(i);
        }
    }
    public static void clearStates() {
        State.clearStates();
    }

    /**
     * Clears states then writes new states to viz readable file
     */
    public static void outputStates(String dir, List<State> states) {
        clearStates(dir);
        for (int i = 0; i < states.size(); i++) {
            System.out.println(i);
            states.get(i).writeToFile(dir, i);
        }
    }
    public static void clearStates(String dir) {
        State.clearStates(dir);
    }

    public static void printStatesToCommandLine(List<State> states) {
        for (State s: states) {
            delay(150);
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

}
