package src;


public class Combing extends Algorithm {
    private boolean isSlidVertical;
    private boolean isSlidHorizontal;
    private int dir;

    /**
     * make wall
     */
    public Combing() {
        super();
    }

    /**
     * Init function for combing algorithm, note that robots must be in
     * [x][y] up, right positive direction.
     */
    public Combing(boolean[][] in, boolean[][] out, boolean expanded, int dir) {
        super(in, out, expanded);
        this.dir = dir;
        Wall w = new Wall(currentState, dir);
        System.out.println(w);
        while (!w.hasReachedEnd()) {
            //label wall meta-modules moving or stationary
            moveWall(w);
            w.update(currentState);
            System.out.println(w);
        }
    }

    /**
     * Moves wall down one level.
     *
     * There is a case where we must slide a module down on both sides
     *
     * If a module has 2 neighbors to slide on, we will slide on both, with
     *   one half of the module sliding on each (in parallel)
     */
    public void moveWall(Wall w) {
        Module[] wallModules = w.getWallModules();
        Boolean[] isMoving = w.getIsMoving();
        for (int i = 0; i < wallModules.length; i++) {
            if (isMoving[i] != null && isMoving[i] == true) {
                currentState.slide(wallModules[i], dir, false);
            }
        }
    }

    @Override
    protected ParallelMove determinePMove() {
/*        if (sliding) {*/
/**/
/*        }*/
        return null;
    }
    
}
    