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

    public Combing(boolean[][] in, boolean[][] out, boolean expanded, int dir) {
        super(in, out, expanded);
        wall = new Wall(currentState, dir);

    }


    @Override
    protected ParallelStep determineParallelStep() {
/*        if (sliding) {*/
/**/
/*        }*/
        return null;
    }
    
}
    
