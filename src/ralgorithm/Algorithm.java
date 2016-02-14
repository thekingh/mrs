
package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract class for algorithms involving reconfiguration of Robot Objects.
 *
 * Designed in to be parallelizable in steps. An algorithm can have a number of
 * functional steps for each reconfiguration. For example a "slide" step is broken
 * up into a bunch of machine processable steps. 
 */
public abstract class Algorithm {

    protected final Robot r;

    Algorithm(Robot r) {
        this.r = r;
    }

    public abstract boolean isComplete();

    public abstract ParallelMove determinePMove();

    public List<State> run() {
        List<State> allStates = new ArrayList<State>();
        ParallelMove pm;
        while (!isComplete()) {
            pm = determinePMove();
            allStates.addAll(pm.pmove());
        }
        return allStates;
    }
}
