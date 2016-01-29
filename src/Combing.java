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
        wall = new Wall(r);
    }

    @Override
    protected ParallelStep determineParallelStep() {
        if (sliding) {

        }
    }
    
}
    
