package src;

import java.util.List;
import java.util.ArrayList;

public class PushIn implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 8; //TODO ????

    private final Robot r;
    private final Module m1;
    private final Module m2;
    private final int dir;
    private final int pushDir;
    private final Unit[] units1;
    private final Unit[] units2;

    public PushIn(Robot r, Module m, int dir, int pushDir) {
    	this.r = r;
        this.m1 = m;
        this.m2 = (Module) m.getNeighbor(dir);
        this.dir = dir;
        this.pushDir = pushDir;

        units1 = new Unit[4];
        units1[0] = m1.getUnitInQuadrant(dir, pushDir);
        units1[1] = m1.getUnitInQuadrant(dir, Direction.opposite(pushDir));
        units1[2] = m1.getUnitInQuadrant(Direction.opposite(dir), Direction.opposite(pushDir));
        units1[3] = m1.getUnitInQuadrant(Direction.opposite(dir), pushDir);

        units2 = new Unit[4];
        units2[0] = m2.getUnitInQuadrant(dir, pushDir);
        units2[1] = m2.getUnitInQuadrant(dir, Direction.opposite(pushDir));
        units2[2] = m2.getUnitInQuadrant(Direction.opposite(dir), Direction.opposite(pushDir));
        units2[3] = m2.getUnitInQuadrant(Direction.opposite(dir), pushDir);
    }

    // TODO: make sure to set Module.hasInside and add to testing
    // (also add has inside for robot equality)
    public void step() {
        switch (currStep) {
            case 0:
                // disconnect from everything on the outside
                r.disconnect(units1[1], Direction.opposite(pushDir));
                r.disconnect(units1[2], Direction.opposite(pushDir));
                r.disconnect(units1[3], pushDir);
                r.disconnect(units2[0], dir);
                r.disconnect(units2[1], Direction.opposite(pushDir));
                r.disconnect(units2[2], Direction.opposite(pushDir));

                // disconnect inside modules
                r.disconnect(units1[0], units2[3]);
                r.disconnect(units2[2], units2[3]);
                break;
            case 1:
                r.extend(units2[0], units2[1]);
                break;
            case 2:
                r.disconnect(units1[0], pushDir);
                r.disconnect(units1[0], units1[1]);
                r.disconnect(units2[1], dir);
                // TODO: need to get the units to connect to
                // r.connect(units2[0], dir);
                // r.connect(units2[3], (dir + 2) % 4);
                break;
            case 3:
                r.extend(units1[0], units1[3]);
                break;
            case 4:
                r.connect(units1[0], units2[3], pushDir);
                r.disconnect(units2[1], units2[2]);
                break;
            case 5:
                r.contract(units2[0], units2[1]);
                break;
            case 6:
                r.contract(units1[0], units1[3]);
            case 7:
                // TODO: need to connect to outsides
                r.connect(units1[0], units1[1], Direction.opposite(pushDir));
                r.connect(units2[1], units2[2], Direction.opposite(pushDir));
                r.connect(units1[0], units2[1], dir);
                break;

        }

        currStep++;
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


                // case 0:
            
            //     r.getUnitGraph().removeEdge(units1[0], units2[3]);
            //     r.getUnitGraph().removeEdge(units2[2], units2[3]);
            //     r.getUnitGraph().removeEdge(units1[0], units1[1]);
            //     break;
            // case 1:
            //     units2[1].extend((dir - 1 + 4) % 4);
            //     break;
            // case 2:
            //     units1[3].extend(dir);
            //     break;
            // case 3:
            // //  NEEDSWORK: can we add and remove in same step??
            //     r.getUnitGraph().addEdge(units1[0], units2[3], (dir - 1 + 4) % 4);
            //     r.getUnitGraph().removeEdge(units2[1], units2[2]);
            //     break;
            // case 4:
            //     units2[1].contract((dir - 1 + 4) % 4);
            //     break;
            // case 5:
            //     units1[3].contract(dir);
            //     break;
            // case 6:
            //     r.getUnitGraph().addEdge(units2[1], units2[2], (dir + 1) % 4);
            //     break;
}