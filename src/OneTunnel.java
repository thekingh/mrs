package src;

import java.util.List;
import java.util.ArrayList;

/**
 * The pushin movement is a 1-tunnel.
 *******************************************************************************
 * Visual Aid: Modules are labeled with either a letter or number. Where A, B, C
 * are the modules being 1-tunneled.
 *     START      END
 *      6          
 *     0A5        065
 *     1B4        1AB4
 *      23         23
 *
 * Requirements:
 *      0 exists iff 1 exists
 *      2 exists iff 3 exists
 *      To keep connectedness throughout the pushin the following at least one
 *      of the following must be true:
 *          Module 5 exists
 *          Module 0, 1 exist and are connected, and Module 2,3 exist and are connected
 *
 *
 * Invarients:
 *      Module A will always be connected to Module 6 through 2 connections
 *      Module C will always be connected to Module 4 through 2 connections
 *      Connectedness will be maintained throughout the 1-tunnel between modules
 *      0, 1, 2, 3, 5
 * 
 */
public class OneTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 29; //TODO ????

    private final Robot r;
    private final Module mA;
    private final Module mB;
    private final Module[] outerMs;
    private final int dir;
    private final int pushDir;
    private final Unit[] unitsA;
    private final Unit[] unitsB;
    private final Unit[][] outerUs;

    public OneTunnel(Robot r, Module m, int dir, int pushDir) {
    	this.r = r;
        this.mA = m;
        this.mB = (Module) m.getNeighbor(dir);
        this.dir = dir;
        this.pushDir = pushDir;

        unitsA = mA.getUnitsFrom(dir, pushDir);
        unitsB = mB.getUnitsFrom(dir, pushDir);

        outerMs = new Module[7];
        outerMs[0] = (Module) mA.getNeighbor(Direction.opposite(pushDir));
        outerMs[1] = (Module) mB.getNeighbor(Direction.opposite(pushDir));
        outerMs[2] = (Module) mB.getNeighbor(dir);
        outerMs[3] = (outerMs[2] == null) ? null : (Module) outerMs[2].getNeighbor(pushDir);
        outerMs[4] = (Module) mB.getNeighbor(pushDir);
        outerMs[5] = (Module) mA.getNeighbor(pushDir);
        outerMs[6] = (Module) mA.getNeighbor(Direction.opposite(dir));
        
        outerUs = new Unit[7][4];
        for (int i = 0; i < outerMs.length; i++) {
            if (outerMs[i] != null) {
                outerUs[i] = outerMs[i].getUnitsFrom(dir, pushDir);
            }
        }
    }

    /**
     * Initializes a 1-tunnel move from a start coordinate to an end coordinate
     * in the module graph.
     * To figure out the actual tunnel position, we rely on the fact that the start
     * node is a leaf node, and therefore only has a single neighbor direction 
     * which we use to determine the tunnel path of the 2 possiblities.
     */
    public static OneTunnel initFromCoords(Robot r, Coordinate start, Coordinate end) {
        Module[][] ms = r.toModuleArray();
        //find startM
        Module startM = ms[start.x()][start.y()];
        int dX = end.x() - start.x();
        int dY = end.y() - start.y();

        //find dir
        int dir = -1;
        for (int i = 0; i < Direction.MAX_DIR; i++) {
            if (startM.hasNeighborInDirection(i)) { 
                dir = i;
            }
        }
        if (dir == -1) {
            throw new RuntimeException("start node has no neighbors");
        }

        //find pushDir
        int pushDir;
        if (Direction.isVertical(dir)) {
            pushDir = dX > 0 ? 1 : 3;
        } else {
            pushDir = dY > 0 ? 0 : 2;
        }

        //find turning module
        Module m;
        switch (dir) {
            case 0:
                m = ms[start.x()][end.y() - 1];
                break;
            case 1:
                m = ms[start.x() - 1][end.y()];
                break;
            case 2:
                m = ms[start.x()][end.y() + 1];
                break;
            case 3:
                m = ms[start.x() + 1][end.y()];
                break;
            default:
                m = null;
                break;
        }
        System.out.println("dir: " + dir);
        System.out.println("pushDir: " + pushDir);
        
        return new OneTunnel(r, m, dir, pushDir);
    }


    // TODO: make sure to set Module.hasInside and add to testing
    // (also add has inside for robot equality)
    public void step() {
        switch (currStep) {
            case 0:
                // disconnect from everything on the outside
                r.disconnect(unitsA[2], Direction.opposite(pushDir));
                r.disconnect(unitsA[3], pushDir);
                r.disconnect(unitsB[0], dir);
                r.disconnect(unitsB[1], Direction.opposite(pushDir));
                r.disconnect(unitsB[2], Direction.opposite(pushDir));

                // disconnect inside modules
                r.disconnect(unitsA[0], unitsB[3]);
                r.disconnect(unitsB[2], unitsB[3]);
                break;
            case 1:
                r.extend(unitsB[0], unitsB[1]);
                break;
            case 2:
                //connect
                r.connect(unitsB[0], outerUs[3][2], dir);
                r.connect(unitsB[3], outerUs[5][1], Direction.opposite(dir));
                //inside disconnect
                r.disconnect(unitsA[0], unitsA[1]);
                //outside disconnect
                r.disconnect(unitsA[0], pushDir);
                r.disconnect(unitsB[1], dir);
                break;
            case 3:
                r.extend(unitsA[0], unitsA[3]);
                break;
            case 4:
                r.connect(unitsA[0], unitsB[3], pushDir);
                r.disconnect(unitsB[1], unitsB[2]);
                break;
            case 5:
                r.contract(unitsB[0], unitsB[1]);
                break;
            case 6:
                r.extend(unitsA[1], unitsB[2]);
                break;
            case 7:
                r.connect(unitsB[2], outerUs[1][0], Direction.opposite(pushDir));
                r.disconnect(unitsA[1], Direction.opposite(pushDir));
                r.connect(unitsB[2], unitsB[1], pushDir);
                break;
            case 8:
                r.contract(unitsA[0], unitsA[3]);
                r.contract(unitsB[2], unitsA[1]);
                break;
            case 9:
                r.disconnect(unitsB[3], Direction.opposite(dir));
                r.disconnect(unitsB[0], dir);

                r.connect(unitsA[3], outerUs[5][1], pushDir);
                r.connect(unitsB[2], outerUs[2][2], dir);
                break;
            case 10:
                r.extend(unitsA[0], unitsB[3]);
                r.extend(unitsB[1], unitsB[2]);
                break;
            case 11:
                r.disconnect(unitsA[3], pushDir);
                r.disconnect(unitsA[1], Direction.opposite(pushDir));
                r.disconnect(unitsB[2], Direction.opposite(pushDir));
                r.disconnect(unitsB[2], Direction.opposite(dir));
                r.disconnect(unitsB[2], dir);

                r.connect(unitsB[3], outerUs[5][0], Direction.opposite(dir));
                r.connect(unitsB[0], outerUs[3][3], dir);
                r.connect(unitsA[2], outerUs[0][0], Direction.opposite(pushDir));
                break;
            case 12:
                r.contract(unitsB[1], unitsB[2]);
                break;
            case 13:
                r.extend(unitsA[1], unitsA[2]);
                break;
            case 14:
                r.connect(unitsA[3], outerUs[5][1], pushDir);
                r.connect(unitsA[1], unitsB[2], pushDir);
                r.connect(unitsA[1], outerUs[1][0], Direction.opposite(pushDir));
                r.disconnect(unitsB[3], dir);
                r.disconnect(unitsB[3], Direction.opposite(dir));
                break;
            case 15:
                r.contract(unitsA[0], unitsB[3]);
                break;
            case 16:
                r.connect(unitsB[3], outerUs[5][1], Direction.opposite(dir));
                r.connect(unitsB[3], unitsB[1], dir);
                r.disconnect(unitsA[1], unitsA[2]);
                r.disconnect(unitsA[0], unitsA[3]);
                break;
            case 17:
                r.extend(unitsB[3], Direction.opposite(pushDir));
                break;
            case 18:
                r.disconnect(unitsA[0], unitsB[3]);
                r.disconnect(unitsA[1], unitsB[2]);
                r.disconnect(unitsB[2], unitsB[1]);
                r.connect(unitsB[2], unitsA[3], Direction.opposite(dir), true);
                r.connect(unitsA[0], unitsA[2], Direction.opposite(dir));
                r.connect(unitsA[0], unitsA[1], dir);
                r.connect(unitsA[1], outerUs[2][2], dir);
                break;
            case 19:
                r.contract(unitsA[3], unitsB[2]);
                break;
            case 20:
                r.connect(unitsB[2], unitsB[3], pushDir);
                r.connect(unitsA[1], unitsB[1], pushDir, true);
                r.disconnect(unitsA[1], Direction.opposite(pushDir));
                r.disconnect(unitsA[0], dir);
                r.disconnect(unitsA[1], dir);
                break;
            case 21:
                r.contract(unitsA[1], unitsB[1]);
                break;
            case 22:
                r.disconnect(unitsB[3], dir);
                r.disconnect(unitsB[3], Direction.opposite(dir));
                r.connect(unitsB[2], unitsA[1], dir);
                break;
            case 23:
                r.extend(unitsA[2], unitsA[0]);
                r.extend(unitsB[2], unitsB[3]);
                break;
            case 24:
                r.disconnect(unitsB[2], Direction.opposite(dir));
                r.connect(unitsA[0], unitsA[1], pushDir);
                r.connect(unitsB[0], unitsB[3], Direction.opposite(dir));
                r.disconnect(unitsB[2], dir);
                break;
            case 25:
                r.contract(unitsB[3], unitsB[2]);
                break;
            case 26:
                r.connect(unitsB[2], outerUs[5][1], Direction.opposite(dir));
                r.connect(unitsB[3], outerUs[5][0], Direction.opposite(dir));
                r.connect(unitsB[2], unitsB[1], dir);
                r.connect(unitsB[1], outerUs[3][2], dir);
                r.connect(unitsB[0], outerUs[3][3], dir);
                r.connect(unitsA[0], outerUs[2][2], dir);
                r.connect(unitsA[1], outerUs[2][3], dir);
                r.connect(unitsA[0], outerUs[1][0], Direction.opposite(pushDir));
                r.connect(unitsA[3], unitsA[1], dir, true);
                r.disconnect(unitsA[2], Direction.opposite(pushDir));
                r.disconnect(unitsA[3], pushDir);
                break;
            case 27:
                r.contract(unitsA[2], unitsA[0]);
                r.contract(unitsA[3], unitsA[1]);
                break;
            case 28:
                r.connect(unitsA[3], unitsB[2], pushDir);
                r.connect(unitsA[2], outerUs[1][3], Direction.opposite(pushDir));
                break;
        }
        //TODO: tunnel out sliding path somehow (don't worry about chunks that
        //may be disconnected, that should be taken care of by alg)
        //TODO: change module graph at the end to reflect the tunnel;

        currStep++;
    }

    public void finalize() {
        r.disconnect(outerMs[0], pushDir);
        r.disconnect(outerMs[1], pushDir);
        r.disconnect(outerMs[2], Direction.opposite(dir));
        r.disconnect(outerMs[3], Direction.opposite(dir));
        r.disconnect(outerMs[4], Direction.opposite(dir));
        r.disconnect(outerMs[5], Direction.opposite(pushDir));
        r.disconnect(outerMs[6], pushDir);
        r.disconnect(outerMs[6], Direction.opposite(pushDir));
        r.disconnect(mA, dir);

        r.connect(outerMs[0], outerMs[6], pushDir);
        r.connect(outerMs[0], outerMs[1], dir);
        r.connect(outerMs[1], mA, pushDir);
        r.connect(outerMs[5], mB, dir);
        r.connect(outerMs[6], outerMs[5], pushDir);
        r.connect(mA, mB, pushDir);
        r.connect(mA, outerMs[2], dir);
        r.connect(mB, outerMs[3], dir);

        mA.swapUnits(unitsA[0], unitsA[1]);
    }

    // /**
    //  * Starting from Modules neighbor, makes a tunnel of disconnected modules
    //  * in the given direction (modules disconnect on edges adjacent to dir)
    //  */
    // private void makeTunnel(Module m, int dir) {

    // }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    /**
     * returns an opposite movement
     */
    public Movement invert() {
        return null;
    }

    public Robot getRobot() {
        return r;
    }
}
