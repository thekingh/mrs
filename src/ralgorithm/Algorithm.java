
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
     * <p>
     * If the method is initilly called in this class then it will
     * be an error, because in an algorithm implementation we want
     * to determine what the pmove is.
     *
     * @return ParallelMove which will be next run in the algorithm
     */
    public ParallelMove determinePMove() {
        throw new RuntimeException("Either Determine PMove or Determine next state must be overwritten.");
    }

    /**
     * Determines the next n states of the robot in the algorithm
     * <p>
     * Often used so that some algorithms can contain other algorithms
     * and determine next states through those lagorithms
     * <p>
     * Default method just calls an implementations determinePMove and
     * runs it.
     *
     * @return A list of robot states to render next
     */
    public List<State> determineNextStates() {
        ParallelMove pm = determinePMove();
        List<State> res = pm.pmove();
        return res;
    }

    /**
     * Runs the algorithm by running all of the Parallel Moves
     *
     * @return A List of Robot States that describe each step of the alg
     */
    public List<State> run() {
        List<State> allStates = new ArrayList<State>();
        List<State> nextStates;
        while (!isComplete()) {
            nextStates = determineNextStates();
            allStates.addAll(nextStates);
        }
        return allStates;
    }

    /**
     * Reverses a list of robot states
     *
     * @param states A list of robot states to reverse
     */
    public static List<State> reverse(List<State> states) {
        int length = states.size();
        List<State> toReturn = new ArrayList<State>();
        for (int i = 0; i < length; i++) {
           toReturn.add(states.get(length - i - 1));
        }
        return toReturn;
    }

    /**
     * Runs the algorithm and reverses the states
     *
     * @return a reverse list of states produced by running the algorithm
     */
    public List<State> runReverse() {
        return reverse(run());
    }
}
