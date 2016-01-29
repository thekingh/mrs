package src;

import java.util.Queue;
import java.util.AbstractQueue;

public class Melt extends Algorithm {
    private Wall wall;
    private final int dir;
    private AbstractQueue<ParallelStep> stepQ;



    public Melt(int dir) {
        wall = new Wall(r, dir);
        this.dir = dir;
        stepQ = new Queue<ParallelStep>();
        
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
