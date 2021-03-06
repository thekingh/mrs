package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Movement that creates a disconnected tunnel from the robot.
 * This allows for the tunnel to be moved with an anchor module in certain
 * movements (i.e. OneTunnel)
 * <p>
 * To connect the tunnel after a move is run, re can simply perform
 * a ConnectAll movement, which connects the modules (in the tunnel)
 * to their new neighbors.
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class MakeTunnel implements Movement {
    private final Robot r;
    private final Module m;
    private final int dir;
    private final int disconnectDir;
    private final int len;
    private boolean reachedEnd;

    /**
     * Makes a tunnel that goes forever.
     */
    public MakeTunnel(Robot r, Module m, int dir, int disconnectDir) {
        this(r, m, dir, disconnectDir, Integer.MAX_VALUE);
    }

    public static MakeTunnel initFromCoords(Robot r, Coordinate c, int dir, int disconnectDir) {
        return new MakeTunnel(r, r.toModuleArray()[c.x()][c.y()], dir, disconnectDir);
    }

    /**
     * Makes a tunnel of a specific length after some module, anchor.
     * <p>
     * the length specifies the number of modules that will disconnect their sides
     *
     * @throws RuntimeException given a null module
     */
    public MakeTunnel(Robot r, Module m, int dir, int disconnectDir, int len) {
        this.r = r;
        this.m = m;
        this.dir = dir;
        this.disconnectDir = disconnectDir;
        this.len = len;
        this.reachedEnd = false;

        if (m == null) {
            throw new RuntimeException("Trying to slide with null module");
        }
    }

    /**
     * Run a single step of the movement.
     * <p>
     * Number of steps is tracked in implementation
     */
    public void step() {
        //Assume m exists
        Module temp = (Module) m.getNeighbor(dir);
        for (int i = 0; i < len; i++) {
            if (temp == null) {
                break;
            }
            r.disconnectModules(temp, disconnectDir);
            temp = (Module) temp.getNeighbor(dir);
        }
        reachedEnd = true;
    }


    /**
     * Determines if the Movement has completed all steps
     * 
     * @return True if done
     */
    public boolean reachedEnd() {
        return reachedEnd;
    }

    /**
     * Inverts a movement by doing to opposite (i.e. Slide in reverse Direction)
     *
     * @return a new Movement that is this inverted
     */
    public Movement invert(Robot s) {
        return null;
    }

    /**
     * Runs final cleanup to update robot, switch units in modules etc.
     */
    public void finalizeMove() {
        return;
    }

    /**
     * @return the robot associated with the movement
     */
    public Robot getRobot() {
        return r;
    }
}
