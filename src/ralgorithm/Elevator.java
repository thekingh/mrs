package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class Elevator implements Movement {
    private final static int NUM_STEPS = 17;

    private final Robot r;
    private final int dir;
    private final int k;
    private final int l;

    private final Module[] lTower;
    private final Module[] rTower;
    private final Module[] elevator;

    private final Unit[][] L;
    private final Unit[][] R;
    private final Unit[][] E;


    private int currStep;

    public Elevator(Robot r, Module m, int dir, int k, int l) {
        this.r = r;
        this.dir = dir;
        this.k = k;
        this.l = l;
        this.currStep = 0;

        lTower = new Module[k];
        L = new Unit[k][4];
        lTower[0] = (Module) m.getNeighbor(Direction.right(dir));
        L[0] = lTower[0].getUnitsFrom(dir, Direction.left(dir));
        for (int i = 1; i < k; i++) {
            lTower[i] = (Module) lTower[i-1].getNeighbor(dir);
            L[i] = lTower[i].getUnitsFrom(dir, Direction.left(dir));
        }

        elevator = new Module[l];
        E = new Unit[l][4];
        elevator[0] = m;
        E[0] = elevator[0].getUnitsFrom(dir, Direction.left(dir));
        for (int i = 1; i < l; i++) {
            elevator[i] = (Module) elevator[i-1].getNeighbor(Direction.left(dir));
            E[i] = elevator[i].getUnitsFrom(dir, Direction.left(dir));
        }

        rTower = new Module[k];
        R = new Unit[k][4];
        rTower[0] = (Module) elevator[l-1].getNeighbor(Direction.left(dir));
        R[0] = rTower[0].getUnitsFrom(dir, Direction.left(dir));
        for (int i = 1; i < k; i++) {
            rTower[i] = (Module) rTower[i-1].getNeighbor(dir);
            R[i] = rTower[i].getUnitsFrom(dir, Direction.left(dir));
        }
    }

    public void step() {

        boolean even = (k % 2) == 0;
        switch(currStep) {
            case 0:
            // TODO: reference specific units for disconnect

                // disconnect left column for contraction
                r.disconnect(L[0][0], Direction.opposite(dir));
                r.disconnect(L[0][0], Direction.right(dir));
                for (int i = 1; i < k - 1; i++) {
                    r.disconnect(L[i][3], Direction.right(dir));
                    r.disconnect(L[i][0], Direction.right(dir));
                }
                r.disconnect(L[k-1][3], Direction.right(dir));

                // disconnect bottom part of elevator, and elevator from right column
                for (int i = 0; i < l; i++) {
                    r.disconnect(E[i][0], Direction.opposite(dir));
                    r.disconnect(E[i][1], Direction.opposite(dir));
                }
                r.disconnect(E[l-1][0], Direction.left(dir));

                break;
            case 1:
                for (int i = 0; i < k-1; i++) {
                    r.contract(L[i][3], L[i][0]);
                    r.contract(L[i][0], L[i+1][3]);
                }
                r.contract(L[k-1][3], L[k-1][0]);
                break;
            case 2:
                if (!even) {
                    r.connect(E[l-1][0], R[k/2][1], Direction.left(dir), true);
                } else {
                    r.connect(E[l-1][0], R[k/2][2], Direction.left(dir), true);
                }

                r.disconnect(E[0][1], Direction.right(dir));
                break;
            case 3:
                for (int i = 0; i < k-1; i++) {
                    r.extend(L[i][3], L[i][0]);
                    r.extend(L[i][0], L[i+1][3]);
                }
                r.extend(L[k-1][3], L[k-1][0]);
                break;
            case 4:
                r.connect(L[0][3], L[0][0], dir, true);

                r.disconnect(L[k-1][0], L[k-1][1]);
                break;
            case 5:
                for (int i = 1; i < k; i++) {
                    r.contract(L[i][3], L[i][0]);
                    r.contract(L[i][3], L[i-1][0]);
                }
                break;
            case 6:
                r.connect(E[0][1], L[k-1][0], Direction.right(dir), true);

                r.disconnect(E[l-1][0], Direction.left(dir));
                break;
            case 7:
                for (int i = 1; i < k; i++) {
                    r.extend(L[i][3], L[i][0]);
                    r.extend(L[i][3], L[i-1][0]);
                }
                break;
            case 8:
                r.connect(L[k-1][0], L[k-1][1], Direction.right(dir), true);
                r.connect(E[l-1][0], R[k-1][1], Direction.left(dir), true);

                r.disconnect(L[0][2], L[0][3]);
                r.disconnect(E[l-1][3], Direction.left(dir));
                break;
            case 9:
                for (int i = 1; i < k; i++) {
                    r.contract(L[i][3], L[i][0]);
                    r.contract(L[i][3], L[i-1][0]);
                }
                break;
            case 10:
                if (!even) {
                    r.connect(E[l-1][3], R[k/2][2], Direction.left(dir), true);
                } else {
                    r.connect(E[l-1][3], R[k/2 - 1][1], Direction.left(dir), true);
                }

                r.disconnect(E[0][2], Direction.right(dir));
                break;
            case 11:
                for (int i = 1; i < k; i++) {
                    r.extend(L[i][3], L[i][0]);
                    r.extend(L[i][3], L[i-1][0]);
                }
                break;
            case 12:
                r.connect(L[0][2], L[0][3], Direction.left(dir), true);

                r.disconnect(L[k-1][3], L[k-1][0]);
                break;
            case 13:
                for (int i = 0; i < k - 1; i++) {
                    r.contract(L[i][3], L[i][0]);
                    r.contract(L[i][0], L[i+1][3]);
                }
                break;            
            case 14:
                r.connect(L[k-1][3], E[0][2], Direction.left(dir), true);

                r.disconnect(E[l-1][3], Direction.left(dir));
                break;            
            case 15:
                for (int i = 0; i < k - 1; i++) {
                    r.extend(L[i][3], L[i][0]);
                    r.extend(L[i][0], L[i+1][3]);
                }
                break;
            case 16:
                r.connect(L[k-1][3], E[0][2], Direction.left(dir), true);
                r.connect(E[l-1][3], R[k-1][2], Direction.left(dir), true);

                // connect left column
                for (int i = 0; i < k; i++) {
                    r.connect(L[i][2], L[i][3], Direction.left(dir), true);
                    r.connect(L[i][1], L[i][0], Direction.left(dir), true);
                }
                r.connect(L[k-1][3], L[k-1][0], dir, true);

                for (int i = 0; i < l; i++) {
                    r.connect(E[i][2], E[i][1], dir, true);
                    r.connect(E[i][3], E[i][0], dir, true);
                }

                break;
                
                // TODO: may want to make tunnel above and below
        }

        currStep++;
    }


    /**
     * Determines if the Movement has completed all steps
     * 
     * @return True if done
     */
    public boolean reachedEnd() {
        // TODO: change
        return currStep >= NUM_STEPS;
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
        r.disconnectModules(elevator[0], Direction.right(dir));
        r.disconnectModules(elevator[l-1], Direction.left(dir));

        r.connectModules(elevator[0], lTower[k-1], Direction.right(dir), true);
        r.connectModules(elevator[l-1], rTower[k-1], Direction.left(dir), true);
    }

    /**
     * @return the robot associated with the movement
     */
    public Robot getRobot() {
        return r;
    }
}