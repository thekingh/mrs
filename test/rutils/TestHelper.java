package rutils;

import rgraph.*;
import ralgorithm.*;
import java.util.List;
import java.util.ArrayList;

/**
 * TestHelper provides a number of helper functions useful in testing robot algorithms.
 */
public final class TestHelper {
    /**
     * This function prints a robot on the console
     * @param r Robot object to print
     */
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

    /**
     * This function prints a list of state objects to the command line.
     * <p>
     * It prints with a default delay of 150 milliseconds between states so that each state is individually recognizable.
     * @param states a java list of states
     */
    public static void printStatesToCommandLine(List<State> states) {
        for (State s: states) {
            delay(150);
            s.printToCommandLine();
        }
    }

    /**
     * delays the code for a number of milliseconds.
     * @param millis delay in milliseconds.
     */
    private static void delay(int millis) {
        try {
            Thread.sleep(millis); 
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Runs and displays on the console a given movement on its embedded robot.
     * @param m Movement to run
     */
    public static void runAndDisplayMove(Movement m) {
        Robot r = m.getRobot();
        while (!m.reachedEnd()) {
            m.step();
            // NEEDWORK: test this more
            // assert r.isConnected();
            r.drawUnit();
            System.out.println(RobotStats.getAll(r));
        }
        m.finalizeMove();
        System.out.println("Finalizing");
        r.drawUnit();
        System.out.println(RobotStats.getAll(r));
        r.drawModule();
    }

    /**
     * Runs and displays on the console a given movement on its embedded robot for s steps.
     * @param m Movement to run
     * @param s number of steps to run and display
     */
    public static void runAndDisplayMoveForSteps(Movement m, int s) {
        Robot r = m.getRobot();
        for (int i = 0; i < s; i++) {

            m.step();
            // assert r.isConnected();
            r.drawUnit();
            System.out.println(RobotStats.getAll(r));

        }
        m.finalizeMove();
        r.drawModule();
    }
    /**
     * Runs a movemnt without displaying it on the console, asserting connectedness
     * @param m Movement to run
     * @throws AssertionError if robot is ever not connected in any step.
     */

    public static void runMove(Movement m) {
        while (!m.reachedEnd()) {
            m.step();
            assert m.getRobot().isConnected();
        }
        m.finalizeMove();
    }

    /**
     * Runs a movemnt without displaying it on the console, asserting connectedness for s steps.
     * @param m Movement to run
     * @param s number of steps to run
     * @throws AssertionError if robot is ever not connected in any step.
     */
    public static void runMoveForSteps(Movement m, int s) {
        for (int i = 0; i < s; i++) {
            m.step();
            assert m.getRobot().isConnected();
        }
        m.finalizeMove();
    }

    /**
     * Makes a robot object from an integer array, integer array is nicely
     * formatted so that in java it will look like the robot (no flips needed.)
     * @param in the integer array of module locations
     * @param expanded the boolean on whether or not the robot is initially
     *                 expanded
     */
    public static Robot makeBot(int[][] in, boolean expanded) {
        return new Robot(orientArray(in), expanded);
    }

    /**
     * Makes a robot object from an integer array, integer array is nicely
     * formatted so that in java it will look like the robot (no flips needed.)
     * @param in the integer array of module locations
     */
    public static Robot makeBot(int[][] in) {
        return makeBot(in, false);
    }

    /**
     * Tests the a robot for a connectedness.
     * @param r robot to test
     * @param connected expected connectedness state of the robot
     * @throws AssertionError throws assertion error if robot was not as expected
     */
    public static void testConnectedness(Robot r, boolean connected) {
        assert r.isConnected() == connected;
        System.out.println("Connectedness Test Passed");
    }

    /**
     * Verifys that two robots are equal in shape
     * @param r robot 1
     * @param s robot 2
     * @return True iff robot r matches robot s
     */
    public static boolean verifyShape(Robot r, Robot s) {
        return r.equals(s);
    }

    /**
     * Verifys that two robots are equal in shape, prints whether this is true.
     * @param r robot 1
     * @param s robot 2
     *
     */
    public static void validateOutput(Robot r, Robot s) {
        if (verifyShape(r, s)) {
            System.out.println("OK");
        } else {
            throw new RuntimeException("NOT OK: Robot shapes do not match");
        }
    }

    /**
     * Orients a boolean array to the correct direction. 
     * <p> 
     * This function is necessary as a static array in java is oriented not in
     * the expected way that a robot is represented (x,y), it is represented
     * (y,x) we must therefore orient the static boolean arrays for testing so 
     * that the shape displayed in the visualizer or on the command line matches
     * what was expected when the test robot was entered.
     * <p>
     * Note that orienting an array is the same as inverting it then mirroring it
     * @param in The unoriented boolean array
     * @return The oriented boolean array
     */
    private static boolean[][] orientArray(boolean[][] in) {
        return mirrorArray(invertArray(in));
    }

    
    /**
     * Orients an int array to the correct direction. 
     * <p> 
     * This function is necessary as a static array in java is oriented not in
     * the expected way that a robot is represented (x,y), it is represented
     * (y,x) we must therefore orient the static boolean arrays for testing so 
     * that the shape displayed in the visualizer or on the command line matches
     * what was expected when the test robot was entered.
     * <p>
     * Note that orienting an array is the same as inverting it then mirroring it
     * @param in The unoriented array
     * @return The oriented 2d array
     */
    private static boolean[][] orientArray(int[][] in) {
        return mirrorArray(invertArray(convertIntToBool(in)));
    }

    /**
     * Inverts an array, switching from (y, x) referencing to (x, y) referencing
     * or vis versa.
     * @param in The unoriented array
     * @return The oriented 2d array
     */
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
    /**
     * mirrors the array across the y axis
     */
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

    /**
     * Converts an integer array to a boolean 2d array
     */
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
     * Clears states then writes new states to viz readable file.
     * Note that this does not automatically clear states, unexpected behavior
     * may happen if folder is occupied.
     * @param states list of states to write to files. 
     */
    public static void outputStates(List<State> states) {
        //clearStates();
        for (int i = 0; i < states.size(); i++) {
            System.out.println(i);
            states.get(i).writeToFile(i);
        }
    }

    /**
     * clears the state folder of past states
     */
    public static void clearStates() {
        State.clearStates();
    }
}
