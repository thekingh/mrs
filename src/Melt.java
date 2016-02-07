package src;

import java.util.List;
import java.util.ArrayList;

public class Melt extends Algorithm {
    private final Wall w;
    private final int dir;

    public Melt(Robot r, int dir) {
        super(r);
        this.w = new Wall(r, dir);
        this.dir = dir;
    }

    public boolean isComplete() {
        return w.hasReachedEnd();
    }

    private boolean isVertical(int dir) {
        return dir % 2 == 0;
    }

    @Override
    public ParallelMove determinePMove() {
        w.update(r);

        Module[] wallModules = w.getWallModules();
        Boolean[] isMoving = w.getIsMoving();
        List<Movement> moves = new ArrayList<Movement>();
        int neighborDir;
 
        //Initialize a Slide for all moving blocks next to stationary blocks
        for (int i = 0; i < wallModules.length - 1; i++) {
            if (isMoving[i] == null || isMoving[i + 1] == null) {
                continue;
            }
            if (isMoving[i] && !isMoving[i + 1]) {
                //wall determins neighbor dir, right or down if MS blocks
                neighborDir = isVertical(dir) ? 1 : 0; 
                moves.add(new Slide(r, wallModules[i], dir, neighborDir));
            } else if (!isMoving[i] && isMoving[i + 1]) {
                neighborDir = isVertical(dir) ? 3 : 2; 
                moves.add(new Slide(r, wallModules[i + 1], dir, neighborDir));
            }
        }

        return new ParallelMove(r, moves);
    }
}
