package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of a movement which contracts all the arms
 * in the robot
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class ContractAll implements Movement {
    private int currStep = 0;
    private static final int NUMSTEPS = 1;
    private final Robot r;

    public ContractAll(Robot r) {
        this.r = r;
    }

    /**
     * This Movement only takes 1 timestep, because all of the arms
     * in the robot can be contracted in parallel
     */
    public void step() {
        if (r.isExpanded()) {
            r.contractAll();
        }
        currStep++;
    }

    public void finalizeMove() {
        return;
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    //TODO IMPLEMENT
    public Movement invert(Robot s) {
        return new ExpandAll(s);
    }

    public Robot getRobot() {
        return r;
    }
}
