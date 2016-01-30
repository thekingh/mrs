package src;

import java.util.Queue;
import java.util.LinkedList;

public class Melt extends Algorithm {
    private Wall wall;
    private final int dir;
    /* TODO made them all linkedlist, should be fine, has all same funcs */
    private LinkedList<ParallelStep> stepQ;



    public Melt(int dir) {
        wall = new Wall(currentState, dir);
        this.dir = dir;
        stepQ = new LinkedList<ParallelStep>();
        
    }

    @Override
    protected ParallelStep determineParallelStep() {
        ParallelStep next;
        if (stepQ.isEmpty()) {
            //figure out the step/populate the parallel step Q
        }
        next = stepQ.pop();
        return next;
    }


}
