package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

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
public class CornerOneTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 34;

    private final Robot r;
    private final Module mA;
    private final Module mB;
    private final Module[] outerMs;
    private final int dir;
    private final int pushDir;
    private final Unit[] unitsA;
    private final Unit[] unitsB;
    private final Unit[][] outerUs;

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
    public CornerOneTunnel(Robot r, Module m, int dir, int pushDir) {
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
    public static CornerOneTunnel initFromCoords(Robot r, Coordinate start, Coordinate end) {
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
        
        return new CornerOneTunnel(r, m, dir, pushDir);
    }

    /**
     * Steps through the OneTunnel by incrementally connecting, etc. units
     */
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
                r.disconnect(unitsA[3], unitsA[0]);
                r.disconnect(unitsB[3], Direction.opposite(dir));
                r.disconnect(unitsB[0], dir);

                r.connect(unitsA[3], outerUs[5][1], pushDir);
                r.connect(unitsB[2], outerUs[2][2], dir);
                break;
            case 10:
                r.extend(unitsA[2], unitsA[3]);
                break;
            case 11:
                r.connect(unitsA[2], unitsA[1], dir);
                r.connect(unitsA[1], unitsB[2], dir);
                r.connect(unitsB[2], unitsB[1], pushDir);
                r.connect(unitsA[0], unitsB[1], dir);
                r.connect(unitsA[3], unitsB[3], dir);
                r.disconnect(unitsA[2], unitsA[3]);
                r.disconnect(unitsA[0], unitsA[1]);
                r.disconnect(unitsA[0], unitsB[3]);
                break;
            case 12:
                r.extend(unitsA[0], unitsB[1]);
                break;
            case 13:
                r.connect(unitsA[0], unitsA[2], Direction.opposite(pushDir));
                r.connect(unitsA[0], unitsA[3], pushDir);
                r.disconnect(unitsB[2], unitsB[1]);
                r.disconnect(unitsB[1], unitsB[0]);
                break;
            case 14:
                r.contract(unitsA[0], unitsB[1]);
                break;
            case 15:
                r.connect(unitsB[1], unitsB[3], pushDir);
                r.connect(unitsB[2], unitsB[0], pushDir, true);
                r.disconnect(unitsA[1], unitsB[2]);
                break;
            case 16:
                r.contract(unitsB[0], unitsB[2]);
                break;
                //No connection steps
            case 17:
                r.extend(unitsA[1], unitsA[2]);
                break;
            case 18:
                r.connect(unitsA[1], unitsB[2], pushDir);
                r.disconnect(unitsA[2], unitsA[0]);
                break;
            case 19:
                r.contract(unitsA[1], unitsA[2]);
                break;
                //TODO write outside connections
            case 20:
                r.connect(unitsA[2], unitsB[1], pushDir);
                r.disconnect(unitsB[1], unitsA[0]);
                break;
            case 21:
                r.extend(unitsA[0], unitsA[3]);
                break;
            case 22:
                r.connect(unitsA[0], unitsA[2], dir);
                r.disconnect(unitsA[3], unitsB[3]);
                break;
            case 23:
                r.contract(unitsA[0], unitsA[3]);
                break;
            //handling outside connections.
            //TODO figure out why this arm isnt showing
            case 24:
                r.connect(unitsB[1], unitsB[3], pushDir);
                r.disconnect(unitsB[2], unitsB[1]);
                r.disconnect(unitsB[0], dir);
                r.disconnect(unitsB[2], dir);
                r.disconnect(unitsB[3], Direction.opposite(dir));
                break;
            case 25:
                r.extend(unitsA[1], unitsB[2]);
                r.extend(unitsB[1], unitsB[3]);
                break;
            case 26:
                r.connect(unitsB[1], unitsB[3], pushDir, true);
                r.connect(unitsB[3], outerUs[5][0], Direction.opposite(dir));
                r.connect(unitsB[0], outerUs[3][3], dir);
                r.connect(unitsB[2], outerUs[3][2], dir);
                r.disconnect(unitsA[1], unitsA[2]);
                r.disconnect(unitsA[1], dir);
                r.disconnect(unitsA[1], Direction.opposite(pushDir));
                break;
            case 27:
                r.contract(unitsB[2], unitsA[1]);
                break;
            case 28:
                r.connect(unitsA[1], unitsB[1], dir);
                r.connect(unitsA[3], unitsB[1], dir);
                r.disconnect(unitsA[2], unitsB[1]);
                r.disconnect(unitsA[2], Direction.opposite(pushDir));
                break;
            case 29:
                r.extend(unitsA[0], unitsA[2]);
                break;
            case 30:
                r.disconnect(unitsA[3], unitsB[1]);
                r.disconnect(unitsA[1], unitsB[1]);
                r.connect(unitsA[2], unitsA[1], pushDir);
                break;
            case 31:
                r.contract(unitsB[3], unitsB[1]);
                break;
            case 32:
                r.connect(unitsA[3], unitsA[1], dir, true);
                r.disconnect(unitsA[3], pushDir);
                r.disconnect(unitsA[0], Direction.opposite(pushDir));
                break;
            case 33:
                r.contract(unitsA[1], unitsA[3]);
                r.contract(unitsA[2], unitsA[0]);
                break;
            case 34:
                r.connect(unitsA[3], unitsB[1], pushDir);
                r.connect(outerUs[1][3], unitsA[0], pushDir);
                r.connect(outerUs[1][0], unitsA[2], pushDir);
                r.connect(unitsA[2], outerUs[2][2], dir);
                r.connect(unitsA[1], outerUs[2][3], dir);
                break;
        }
        //TODO: tunnel out sliding path somehow (don't worry about chunks that
        //may be disconnected, that should be taken care of by alg)
        //TODO: change module graph at the end to reflect the tunnel;

        currStep++;
    }

    /**
     * Finalizes the OneTunnel movement
     * <p>
     * Connects all of the modules to the appropriate neighbors, given the
     * tunnel performed
     */
    public void finalizeMove() {
        // NOTE: switch units before modules, if not, module connections
        // will permute units in undesired ways
        // TODO: make finalize units and run that first, and finalize modules
        mA.swapUnits(unitsA[0], unitsA[1]);
        mA.swapUnits(unitsA[0], unitsA[2]);

        mB.swapUnits(unitsB[1], unitsB[2]);
        //TODO switch modules
        r.disconnectModules(outerMs[0], pushDir);
        r.disconnectModules(outerMs[1], pushDir);
        r.disconnectModules(outerMs[2], Direction.opposite(dir));
        r.disconnectModules(outerMs[3], Direction.opposite(dir));
        r.disconnectModules(outerMs[4], Direction.opposite(dir));
        r.disconnectModules(outerMs[5], Direction.opposite(pushDir));
        r.disconnectModules(outerMs[6], pushDir);
        r.disconnectModules(outerMs[6], Direction.opposite(pushDir));
        r.disconnectModules(mA, dir);

        r.connectModules(outerMs[0], outerMs[6], pushDir);
        r.connectModules(outerMs[0], outerMs[1], dir);
        r.connectModules(outerMs[1], mA, pushDir);
        r.connectModules(outerMs[5], mB, dir);
        r.connectModules(outerMs[6], outerMs[5], pushDir);
        r.connectModules(mA, mB, pushDir);
        r.connectModules(mA, outerMs[2], dir);
        r.connectModules(mB, outerMs[3], dir);
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
