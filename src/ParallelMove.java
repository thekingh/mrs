package src;

import java.util.List;
import java.util.ArrayList;

public class ParallelMove {
    private final List<Movement> moves;
    private final Robot r;

    ParallelMove(Robot r, List<Movement> moves) {
        this.moves = moves;
        this.r = r;
    }

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
        return states;
    }

    /**
     * performs a single timestep of parallel movements.
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
        return moveFinished ? null : r.getState();
    }
}
