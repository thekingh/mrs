package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that represents a Parallel Move, or a series of steps to be run concurrently
 * <p>
 * The parallel move can be broken down to a set of Movements, which will have
 * each sub step of the movement run in parallel. This allows for renderings
 * of a robot between each substep of all parallel moves to aid in
 * visualization of moves happening concurrently
 * <p>
 * If we are given moves {A, B, C} each consisting of two substeps 
 * (represented in Movement class) then we expect the following ordering for
 * the pmove:
 * <ol>
 *   <li>A1, B1, C1</li>
 *   <li>State snapshot</li>
 *   <li>A2, B2, C2</li>
 *   <li>State snapshot</li>
 * </ol>
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class ParallelMove {
    private final List<Movement> moves;
    private final Robot r;

    /**
     * Instantiates a Parallel Move
     *
     * @param r The Robot on which the series of movements will be performed
     * @param moves a List of Movements which will be run in parallel (step be step)
     */
    ParallelMove(Robot r, List<Movement> moves) {
        this.moves = moves;
        this.r = r;
    }

    /**
     * Performs the ParallelMove, including all steps of movements
     *
     * @return a List fo Robot States descirbing the robot after each
     * parallel step
     */
    public List<State> pmove() {
        List<State> states = new ArrayList<State>();
        State state;
        while(true) {
            state = step();
            if (state == null) {
                break;
            }
            states.add(state);
        }
        // runs final step of moves to update Robot
        for (Movement m : moves) {
            m.finalize();
        }

        return states;
    }

    /**
     * performs a single timestep of parallel movements
     * <p>
     * The timestep consists of changes made by all Movements in ParallelMove
     *
     * @return A State object, unless no moves were made in which case it returns null
     */
    private State step() {
        boolean moveFinished = true;
        for (int i = 0; i < moves.size(); i++) {
            if (!moves.get(i).reachedEnd()) {
                moves.get(i).step();
                moveFinished = false;
            }
        }
        if (!moveFinished) {
            r.drawUnit();
            System.out.println(RobotStats.getAll(r));
/*            return r.getState();*/
            return new State(r);
        } else {
            return null;
        }
    }

    /**
     * returns the opposite of a parallel move, meaning the inverse of all Movements
     * <p>
     * Given n moves comprising a ParallelMove the opposite ParallelMove would
     * be that comprising the inverse of all n moves
     *
     * @return an inverted ParallelMove
     */
    public ParallelMove invertPMove(Robot r) {
        List<Movement> invertedMoves = new ArrayList<Movement>();

        for (Movement m : moves) {
            invertedMoves.add(m.invert(r));
        }

        return new ParallelMove(r, invertedMoves);
    }
}
