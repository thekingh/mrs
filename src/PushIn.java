package src;

import java.util.List;
import java.util.ArrayList;

public class PushIn implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 7; //TODO ????

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
        units1[1] = m1.getUnitInQuadrant(dir, (pushDir + 2) % 4);
        units1[2] = m1.getUnitInQuadrant((dir + 2) % 4, (pushDir + 2) % 4);
        units1[3] = m1.getUnitInQuadrant((dir + 2) % 4, pushDir);

        units2 = new Unit[4];
        units2[0] = m2.getUnitInQuadrant(dir, pushDir);
        units2[1] = m2.getUnitInQuadrant(dir, (pushDir + 2) % 4);
        units2[2] = m2.getUnitInQuadrant((dir + 2) % 4, (pushDir + 2) % 4);
        units2[3] = m2.getUnitInQuadrant((dir + 2) % 4, pushDir);
    }

    // TODO: make sure to set Module.hasInside and add to testing
    // (also add has inside for robot equality)
    public void step() {
        switch (currStep) {
            case 0:
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

    public int opposite(int dir) {
        return (dir + 2) % 4;
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