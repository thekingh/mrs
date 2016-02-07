package src;

/*import java.util.Queue;*/
/*import java.util.LinkedList;*/
/**/
public class Melt extends Algorithm {
    private Wall wall;
    private final int dir;
    /* TODO made them all linkedlist, should be fine, has all same funcs */
    private LinkedList<ParallelMove> stepQ;



    public Melt(int dir) {
        wall = new Wall(currentState, dir);
        this.dir = dir;
        stepQ = new LinkedList<ParallelMove>();
        
    }

    @Override
    protected ParallelMove determinePMove() {
        ParallelMove next;
    }


}
