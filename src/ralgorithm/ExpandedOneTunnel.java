package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;


// TODO: change comments
/**
 * The OneTunnel is the most primitive kTunnel from Start to End in one turn.
 * <p>
 * The OneTunnel is the building block for more complex kTunnels, which
 * are effectively a series of 1-Tunnels.
 *******************************************************************************
 * <p>Visual Aid: Modules are labeled with either a letter or number. Where A, B, C
 * are the modules being 1-tunneled.
 * <p>    
 * START <br>
 * &nbsp 6    <br>
 * 0A5   <br>
 * 1B4   <br>
 * &nbsp 23   <p>
 *
 * END   <br>
 * 065   <br>
 * 1AB4  <br>
 * &nbsp 23
 * <p>
 * Requirements:
 * <ul>
 *      <li>0 exists iff 1 exists</li>
 *      <li>2 exists iff 3 exists</li>
 *      <li><ul>To keep connectedness throughout the pushin the following at least one
 *      of the following must be true:
 *          <li>Module 5 exists</li>
 *          <li>Module 0, 1 exist and are connected, and Module 2,3 exist and are connected</li></li>
 *</ul>
 *<p>
 * Invarients:
 * <ul>
 *      <li>Module A will always be connected to Module 6 through 2 connections</li>
 *      <li>Module C will always be connected to Module 4 through 2 connections</li>
 *      <li>Connectedness will be maintained throughout the 1-tunnel between modules
 *      0, 1, 2, 3, 5</li>
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class ExpandedOneTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 37;

    private final Robot r;
    private final Module mA;
    private final Module mB;
    private final Module[] outerMs;
    private final int dir;
    private final int pushDir;
    private final Unit[] A;
    private final Unit[] B;
    private final Unit[][] O;

    /**
     * Instantiate a OneTunnel movement in a simple form of which Module being tunneled
     * <p>
     * Tunnels around a Turning module, which is at the corner or only turn
     *
     * @param r the Robot to tunnel through
     * @param m Module right next to the Turning module which will be pushed in
     * @param dir First direction of tunnel (to mush m in)
     * @param pushDir Second direction of tunnel (push Turning module out)
     */
    public ExpandedOneTunnel(Robot r, Module m, int dir, int pushDir) {
        // System.out.println("INIT");
    	this.r = r;
        this.mA = m;
        this.mB = (Module) m.getNeighbor(dir);
        this.dir = dir;
        this.pushDir = pushDir;

        A = mA.getUnitsFrom(dir, pushDir);
        B = mB.getUnitsFrom(dir, pushDir);

        outerMs = new Module[7];
        outerMs[0] = (Module) mA.getNeighbor(Direction.opposite(pushDir));
        outerMs[1] = (Module) mB.getNeighbor(Direction.opposite(pushDir));
        outerMs[2] = (Module) mB.getNeighbor(dir);
        outerMs[3] = (outerMs[2] == null) ? null : (Module) outerMs[2].getNeighbor(pushDir);
        outerMs[4] = (Module) mB.getNeighbor(pushDir);
        outerMs[5] = (Module) mA.getNeighbor(pushDir);
        outerMs[6] = (Module) mA.getNeighbor(Direction.opposite(dir));
        
        O = new Unit[7][4];
        for (int i = 0; i < outerMs.length; i++) {
            if (outerMs[i] != null) {
                O[i] = outerMs[i].getUnitsFrom(dir, pushDir);
            }
        }
    }

    /**
     * Initializes a 1-tunnel move from a start coordinate to an end coordinate
     * in the module graph.
     * <p>
     * To figure out the actual tunnel position, we rely on the fact that the start
     * node is a leaf node, and therefore only has a single neighbor direction 
     * which we use to determine the tunnel path of the 2 possiblities.
     *
     * @param r Robot tunneling through
     * @param start The starting location of the tunneling Module in r (x,y)
     * where y+ is up and x+ is right
     * @param end Final location, where the module is tunneling to
     */
    public static ExpandedOneTunnel initFromCoords(Robot r, Coordinate start, Coordinate end) {
        Module[][] ms = r.toModuleArray();
        //find startM
        Module startM = ms[start.x()][start.y()];
        int dX = end.x() - start.x();
        int dY = end.y() - start.y();

        //find dir
        int dir = -1;
        for (int i = 0; i < Direction.NUM_DIR; i++) {
            if (startM.hasEdge(i)) { 
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
        
        return new ExpandedOneTunnel(r, m, dir, pushDir);
    }

    /**
     * Steps through the OneTunnel by incrementally connecting, etc. units
     */
    public void step() {
        switch (currStep) {
            case 0:
                r.disconnect(A[0], pushDir);
                r.disconnect(A[1], Direction.opposite(pushDir));
                r.disconnect(A[2], Direction.opposite(pushDir));
                r.disconnect(A[3], pushDir);

                r.disconnect(B[2], Direction.opposite(pushDir));
                r.disconnect(B[3], pushDir);
                break;
            case 1:
                r.contract(B[0], B[3]);
                r.contract(B[1], B[2]);
                r.contract(B[2], A[1]);
                r.contract(B[3], A[0]);
                break;
            case 2:
                r.connect(A[1], O[1][3], Direction.opposite(pushDir), true);
                r.connect(A[0], O[4][2], pushDir, true);

                r.disconnect(B[1], B[2]);
                r.disconnect(B[1], Direction.opposite(pushDir));
                r.disconnect(B[1], dir);
                break;
            case 3:
                r.contract(B[0], B[1]);
                break;
            case 4:
                r.disconnect(B[2], B[3]);
                break;
            case 5:
                r.extend(A[1], B[2]);
                break;
            case 6:
                r.connect(B[2], O[1][0], Direction.opposite(pushDir), true);
                r.connect(B[2], B[1], pushDir);
                r.connect(B[2], O[2][2], dir, true);

                r.disconnect(A[0], A[3]);
                r.disconnect(A[0], B[3]);
                r.disconnect(A[0], pushDir);
                break;
            case 7:
                r.contract(A[1], A[0]);
                break;
            case 8:
                r.connect(A[0], B[1], dir, true);

                r.disconnect(A[1], Direction.opposite(pushDir));
                break;
            case 9:
                r.contract(A[0], B[1]);
                r.contract(A[1], B[2]);
                r.contract(A[1], A[2]);
                break;
            case 10:
                r.connect(A[2], O[1][3], Direction.opposite(pushDir), true);
                r.connect(A[3], O[4][2], pushDir, true);
                r.connect(A[3], B[3], dir);

                r.disconnect(A[2], A[3]);
                r.disconnect(A[0], A[1]);
                r.disconnect(A[0], A[3]);
                break;
            case 11:
                r.extend(A[0], B[1]);
                break;
            case 12:
                r.connect(A[2], A[0], pushDir);
                r.connect(A[0], A[3], pushDir);

                r.disconnect(B[2], B[1]);
                r.disconnect(B[1], B[0]);
                break;
            case 13:
                r.contract(A[0], B[1]);
                break;
            case 14:
                r.connect(A[1], B[1], pushDir);
                r.connect(B[1], B[3], pushDir);
                r.connect(B[2], B[0], pushDir, true);

                r.disconnect(A[1], B[2]);
                r.disconnect(B[2], Direction.opposite(pushDir));
                r.disconnect(B[2], dir);
                break;
            case 15:
                r.contract(B[0], B[2]);
                break;
            case 16:
                r.connect(B[1], B[2], dir);

                r.disconnect(A[1], B[1]);
                break;
            case 17:
                r.extend(A[2], A[1]);
                break;
            case 18:
                r.connect(A[1], B[2], pushDir);
                r.connect(A[1], O[1][0], Direction.opposite(pushDir), true);
                r.connect(A[1], O[2][2], dir, true);

                r.disconnect(A[2], A[0]);
                r.disconnect(A[2], Direction.opposite(pushDir));
                r.disconnect(A[2], Direction.opposite(dir));
                break;
            case 19:
                r.contract(A[1], A[2]);
                break;
            case 20:
                r.connect(A[2], B[1], pushDir);

                r.disconnect(A[0], B[1]);
                break;
            case 21:
                r.extend(A[3], A[0]);
                break;
            case 22:
                r.connect(A[0], O[1][3], Direction.opposite(pushDir), true);
                r.connect(A[0], O[6][1], Direction.opposite(dir), true);
                r.connect(A[0], A[2], dir);

                r.disconnect(A[3], B[3]);
                r.disconnect(A[3], pushDir);
                r.disconnect(A[3], Direction.opposite(dir));
                break;
            case 23:
                r.contract(A[0], A[3]);
                break;
            case 24:
                r.disconnect(B[1], B[3]);
                break;
            case 25:
                r.extend(B[0], B[3]);
                break;
            case 26:
                r.connect(B[3], O[4][2], pushDir, true);

                r.disconnect(A[3], B[1]);
                r.disconnect(A[2], B[1]);
                r.disconnect(B[0], dir);
                break;
            case 27:
                r.extend(A[1], B[2]);
                r.extend(B[2], B[0]);
                break;
            case 28:
                r.connect(B[3], O[5][1], Direction.opposite(dir), true);
                r.connect(B[0], O[3][2], dir, true);
                r.connect(B[2], O[2][3], dir, true);
                break;
            case 29:
                r.extend(B[2], B[1]);
                break;
            case 30:
                r.connect(A[3], B[1], pushDir);
                r.connect(A[1], O[6][0], Direction.opposite(dir), true);
                r.connect(B[1], B[3], pushDir, true);

                r.disconnect(A[2], A[1]);
                r.disconnect(A[1], Direction.opposite(pushDir));
                r.disconnect(A[1], dir);
                break;
            case 31:
                r.contract(B[2], A[1]);
                break;
            case 32:
                r.connect(A[3], A[1], dir, true);
                break;
            case 33:
                r.extend(A[0], A[2]);
                break;
            case 34:
                r.connect(A[2], A[1], pushDir);
                r.connect(A[2], O[1][0], Direction.opposite(pushDir), true);
                r.connect(A[2], O[2][2], dir, true);

                r.disconnect(B[1], Direction.opposite(dir));
                r.disconnect(B[3], Direction.opposite(dir));
                r.disconnect(B[2], dir);
                r.disconnect(B[0], dir);
                break;
            case 35:
                r.extend(A[0], A[3]);
                r.extend(A[2], A[1]);
                r.extend(A[3], B[1]);
                r.extend(A[1], B[2]);
                break;
            case 36:
                r.connect(A[3], O[6][0], Direction.opposite(dir), true);
                r.connect(B[1], O[5][1], Direction.opposite(dir), true);
                r.connect(B[3], O[5][0], Direction.opposite(dir), true);
                r.connect(A[1], O[2][3], dir, true);
                r.connect(B[2], O[3][2], dir, true);
                r.connect(B[0], O[3][3], dir, true);
                break;

        }

        currStep++;

    }

    /**
     * Finalizes the OneTunnel movement
     * <p>
     * Connects all of the modules to the appropriate neighbors, given the
     * tunnel performed
     */
    public void finalize() {
        // NOTE: switch units before modules, if not, module connections
        // will permute units in undesired ways
        // TODO: make finalize units and run that first, and finalize modules
        mA.swapUnits(A[0], A[1]);
        mA.swapUnits(A[0], A[2]);

        mB.swapUnits(B[1], B[2]);

        r.disconnectModules(outerMs[0], pushDir);
        r.disconnectModules(outerMs[1], pushDir);
        r.disconnectModules(outerMs[2], Direction.opposite(dir));
        r.disconnectModules(outerMs[3], Direction.opposite(dir));
        r.disconnectModules(outerMs[4], Direction.opposite(dir));
        r.disconnectModules(outerMs[5], Direction.opposite(pushDir));
        r.disconnectModules(outerMs[6], pushDir);
        r.disconnectModules(outerMs[6], Direction.opposite(pushDir));
        r.disconnectModules(mA, dir);

        // TODO: need to make expanded modules when connecting
        r.connectModules(outerMs[0], outerMs[6], pushDir, true);
        r.connectModules(outerMs[0], outerMs[1], dir, true);
        r.connectModules(outerMs[1], mA, pushDir, true);
        r.connectModules(outerMs[5], mB, dir, true);
        r.connectModules(outerMs[6], outerMs[5], pushDir, true);
        r.connectModules(mA, mB, pushDir, true);
        r.connectModules(mA, outerMs[2], dir, true);
        r.connectModules(mB, outerMs[3], dir, true);
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    /**
     * Inverts the OneTunnel
     *
     * @return OneTunnel from end to start of the same robot
     */
    public Movement invert(Robot s) {
        return null;
    }

    public Robot getRobot() {
        return r;
    }
}
