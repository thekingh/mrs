package src;


public class Combing extends Algorithm {
    private Wall wall;
    private boolean isSlidVertical;
    private boolean isSlidHorizontal;

    /**
     * make wall
     */
    public Combing() {
        super();
/*        wall = new Wall(r);*/
    }

    /**
     * Init function for combing algorithm, note that robots must be in
     * [x][y] up, right positive direction.
     */
    public Combing(boolean[][] in, boolean[][] out, boolean expanded, int dir) {
        super(in, out, expanded);
        Wall w = new Wall(currentState, dir);
        System.out.println(w);
        while (!w.hasReachedEnd()) {
            //label wall meta-modules moving or stationary
            w.update(currentState);

            System.out.println(w);
        }
    }


    @Override
    protected ParallelStep determineParallelStep() {
/*        if (sliding) {*/
/**/
/*        }*/
        return null;
    }
    
}
    
