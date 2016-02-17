package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Reverses a Melting movement of a final Robot in the opposite of the direction
 * <p>
 * Consider Robot s, which is a fully melted robot in one direction (a).
 * Consider Robot f, which is a robot that can be melted in direction a to attain
 *  s in a series of n moves {m0,m1,...,mn}.
 * We can then reverse this melt to transform Robot s into Robot f in direction b,
 *  which is the opposite of the melting direction, by performing the inverse
 *  of each movement in opposite order: {mn_invers,...,m1_inverse,m0_inverse}
 * <p>
 * Performing this backwards order of inverted moves on s, allows us to unmelt
 *  to result in f.
 * <p>
 ********************************************************
 * VISUAL AID for ReverseMelt in dir 3 (up):<p>
 * START, robot s<br>
 * AB D          <br>
 * EFCH          <p>
 * END, robot f  <br>
 * ABCD          <br>
 * EF H          <p>
 * Robot s is the robot that has been melted from f in dir 1 (down) and is
 *  thus being reverse melted to reform s/
 * 
 */
// TODO: need to actually reverse melt robot s somehow, but diff modules
// want to make a copy of it to melt (do we want to have invert take new robot?)
public class ReverseMelt extends Algorithm {
	private final Robot s;
	private final Robot f;
	private final Stack<ParallelMove> stack;

	/**
	 * Instantiates a ReverseMelt algorithm
	 * <p>
	 * Will transform melted robot s, into its unmelted form f by unmelting 
	 *  in dir
	 *
	 * @param s the Robot to perform the reverseMelt on
	 * @param f the Robot that was melted to attain s
	 * @param dir the direction of the reverse melt, which is the opposite of melt from f to s
	 */
	public ReverseMelt(Robot s, Robot f, int dir) {
		super(s);
		this.s = s;
		this.f = f;
		stack = new Stack<ParallelMove>();

		Melt forward = new Melt(f, Direction.opposite(dir));


        ParallelMove pm;
        while (!forward.isComplete()) {
            pm = forward.determinePMove();
            pm.pmove();
            // add the opposite Pmove to Stack for later use
            stack.push(pm.invertPMove());
        }
	}

	@Override
    public boolean isComplete() {
    	return stack.isEmpty();
    }

    @Override
    public ParallelMove determinePMove() {
    	return stack.pop();
    }
}