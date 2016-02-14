package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of the Melt algorithm to melt a robot in some direction
 * <p>
 * Melt is performed by:
 * <ol>
 * <li> Creating a "wall" of Modules at the opposite end of the robot from
 * the direction we're melting in. </li>
 * <li> Continuosly moving that wall in the direction of melt, taking with
 * it all modules that can be slid in that direction </li>
 * <li> The move ends when the wall can no longer be moved down because
 * it reaches the edge of the robot </li>
 * </ol>
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class Melt extends Algorithm {
    private final Wall w;
    private final int dir;

    /**
     * Initializes a Melt Algorithm which melts a Robot in a direction
     * <p>
     * Final state of the Melt leaves no more Modules able to be slid in 
     * the given direction
     *
     * @param r Robot on whcih to perform algorithm
     * @param dir Direction of melt
     */
    public Melt(Robot r, int dir) {
        super(r);
        this.w = new Wall(r, dir);
        this.dir = dir;
    }

    /**
     * Determines if the Melt is finished
     *
     * @return True if the wall has reached the base of the robot in the given
     * direction, ad no more Modules may be slid down
     */
    public boolean isComplete() {
        return w.hasReachedEnd();
    }


    @Override
    /**
     * determines the next Parallel move for the Melt
     *
     * @return a ParallelMove consisting of multiple slides, to be run concurrently,
     * in the Melt direction
     */
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
                neighborDir = Direction.isVertical(dir) ? 1 : 0; 
                moves.add(new Slide(r, wallModules[i], dir, neighborDir));
            } else if (!isMoving[i] && isMoving[i + 1]) {
                neighborDir = Direction.isVertical(dir) ? 3 : 2; 
                moves.add(new Slide(r, wallModules[i + 1], dir, neighborDir));
            }
        }

        return new ParallelMove(r, moves);
    }
}
