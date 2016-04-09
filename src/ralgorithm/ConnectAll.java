
package ralgorithm;

import rgraph.*;
import rutils.*;

/**
 * Connects all modules that have some connectivity to the graph 
 * to any possible neighbor
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class ConnectAll implements Movement {
	private final Robot r;
    private final boolean extended;
	private boolean isFinished = false;

	public ConnectAll(Robot r) {
		this(r, false);
	}

    public ConnectAll(Robot r, boolean extended) {
        this.r = r;
        this.extended = extended;
    }

	/**
     * Run a single step of the movement.
     * <p>
     * Number of steps is tracked in implementation
     */
	// NEEDSWORK: want to connect all Units in Modules too??
	// currently only connects modules.
    public void step() {
    	Module[][] ms = r.toModuleArray();
    	int w = ms.length;
    	int h = ms[0].length;

    	for (int i = 0; i < w; i++) {
    		for (int j = 0; j < h; j++) {
    			// connect up
    			if (j < h - 1) {
    				r.connectModules(ms[i][j], ms[i][j+1], 0, extended);
    			}
    			// connect right
    			if (i < w - 1) {
    				r.connectModules(ms[i][j], ms[i+1][j], 1, extended);
    			}
    			// connect down
    			if (j > 0) {
    				r.connectModules(ms[i][j], ms[i][j-1], 2, extended);
    			}
    			//connect left
    			if (i > 0) {
    				r.connectModules(ms[i][j], ms[i-1][j], 3, extended);
    			}
    		}
    	}

    	isFinished = true;
    }

    /**
     * Determines is the Movement has completed all steps
     * 
     * @return True if done
     */
    public boolean reachedEnd() {
    	return isFinished;
    }

    /**
     * Inverts a movement by doing to opposite (i.e. Slide in reverse Direction)
     *
     * @return a new Movement that is this inverted
     */
    public Movement invert(Robot s) {
    	return null;
    }

    /**
     * Runs final cleanup to update robot, switch units in modules etc.
     */
    public void finalizeMove() {
        return;
    }

    /**
     * @return the robot associated with the movement
     */
    public Robot getRobot() {
    	return r;
    }
}
