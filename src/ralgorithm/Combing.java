package ralgorithm;

import rgraph.*;
import rutils.*;


public class Combing extends Algorithm {
    private int dir; //direction of melt

    /**
     * Init function for combing algorithm, note that robots must be in
     * [x][y] up, right positive direction.
     */
    public Combing(Robot r, int dir) {
        super(r);
        this.dir = dir;
        Wall w = new Wall(r, dir);
        System.out.println(w);
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
/*                r.slide(wallModules[i], dir, false);*/
            }
        }
    }

    @Override
    public boolean isComplete() {

        return false;
    }

    @Override
    public ParallelMove determinePMove() {
/*        moveWall(w);*/
/*        w.update(r);*/
/*        if (sliding) {*/
/**/
/*        }*/
        return null;
    }


}
    
