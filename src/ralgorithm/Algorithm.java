
package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract class for algorithms involving reconfiguration of Robot Objects.
 * <p>
 * Designed in to be parallelizable in steps. An algorithm can have a number of
 * functional steps for each reconfiguration. For example a "slide" step is broken
 * up into a bunch of machine processable steps. 
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public abstract class Algorithm {

    /**
     * Robot algorithm is being run on
     */
    protected final Robot r;

    /**
     * Initializes an Algorithm on a robot
     *
     * @param r The robot on which the algorithm will be performed
     */
    Algorithm(Robot r) {
        this.r = r;
    }

    /**
     * Determines if the algorithm has finished executing
     *
     * @return a boolean value True is algorithm is done
     */
    public abstract boolean isComplete();

    /**
     * Determines the next sequence of moves to be run in parallel
     * <p>
     * Abstract method to be defined in instance of algorithm that is
     * the essential step for the execution of the algorithm.
     *
     * @return ParallelMove which will be next run in the algorithm
     */
    public abstract ParallelMove determinePMove();

    public State determineNextState() {
        pm = determinePMove();
        return pm.pmove();
    }

    /**
     * Runs the algorithm by running all of the Parallel Moves
     *
     * @return A List of Robot States that describe each step of the alg
     */
    public List<State> run() {
        List<State> allStates = new ArrayList<State>();
        State nextState;
        while (!isComplete()) {
            nextState = determineNextState();
            allStates.addAll(nextState);
        }
        return allStates;
    }

    public static List<State> reverse(List<State> states) {
        int length = states.size();
        List<State> toReturn = new ArrayList<State>();
        for (int i = 0; i < length; i++) {
            toReturn.add(states.get(length - i - 1));
        }
        return toReturn;
    }

    public List<State> runReverse() {
        return reverse(run());
    }
}
