package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class CombToLine extends Algorithm {
	private final int dir;
	private LinkedList<ParallelMove> q;

	public CombToLine(Robot r, int dir) {
		super(r);
		this.dir = dir;
		this.q = new LinkedList<ParallelMove>();
	}
	
	@Override
    public boolean isComplete() {
    	return r.toModuleArray()[0].length == 1;
    }

    private void enqueueMoves() {

        // TODO: onetunnel does not yet correctly handle coordinates very well,
        // only does 1 space turns, but need to do long tunnels??


    	Module[][] modules = r.toModuleArray();
    	List<Movement> tunnelMovesEven = new ArrayList<Movement>();
    	List<Movement> tunnelMovesOdd = new ArrayList<Movement>();
    	List<Movement> connectMoves = new ArrayList<Movement>();
    	int w = modules[0].length;
    	Module m;

    	for (int i = 0; i < w; i++) {
    		m = modules[i][1];
    		if (m != null) {
                List<Movement> l = new ArrayList<Movement>();
                l.add(new ExpandedOneTunnel(r, m, 2, 1));
                q.addLast(new ParallelMove(r, l));
	    		// if (i % 2 == 0) {
	    		// 	tunnelMovesEven.add(new ExpandedOneTunnel(r, m, 2, 1));
	    		// } else {
	    		// 	tunnelMovesOdd.add(new ExpandedOneTunnel(r, m, 2, 1));
	    		// }
	    	}
    	}

    	// connect all modules after wall moves
        connectMoves.add(new ConnectAll(r, true));

        //add to queue
        // q.addLast(new ParallelMove(r, tunnelMovesEven));
        // q.addLast(new ParallelMove(r, tunnelMovesOdd));
        // q.addLast(new ParallelMove (r, connectMoves));
    }

    @Override
    public ParallelMove determinePMove() {
    	if (q.isEmpty()) {
            enqueueMoves();
        }
        return q.pollFirst();
    }
}