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
public class ExpandAll implements Movement {
    private int currStep = 0;
    private static final int NUMSTEPS = 1;
    private final Robot r;

    public ExpandAll(Robot r) {
        this.r = r;
    }

    public void step() {
        if (!r.isExpanded()) {
            r.expandAll();
        }
        currStep++;
    }

    public void finalize() {
        return;
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    //TODO IMPLEMENT
    public Movement invert(Robot s) {
        return new ContractAll(s);
    }

    public Robot getRobot() {
        return r;
    }
}
