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
public class FlipConfiguration implements Movement {
    private int currStep = 0;
    private static final int NUMSTEPS = 1;
    private final Robot r;

    public FlipConfiguration(Robot r) {
        this.r = r;
    }

    public void step() {
        if (r.isExpanded()) {
            r.contractAll();
        } else {
            r.expandAll();
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
        return new FlipConfiguration(s);
    }

    public Robot getRobot() {
        return r;
    }
}
