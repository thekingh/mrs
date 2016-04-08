package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class CombToLine extends Algorithm {
	private final int dir;
	private LinkedList<ParallelMove> q;
    private boolean isComplete;

	public CombToLine(Robot r, int dir) {
		super(r);
		this.dir = dir;
		this.q = new LinkedList<ParallelMove>();
        List<Movement> initial_moves = new ArrayList<Movement>();
        initial_moves.add(new ExpandAll(r));
        q.addLast(new ParallelMove(r, initial_moves));

        this.isComplete = false;
	}
	
	@Override
    public boolean isComplete() {
    	return q.isEmpty() && isComplete;
    }

    private void enqueueMoves() {
        if (r.toModuleArray()[0].length == 1) {
            List<Movement> initial_moves = new ArrayList<Movement>();
            initial_moves.add(new ContractAll(r));
            q.addLast(new ParallelMove(r, initial_moves));
            isComplete = true;
            return;
        }

        Module[][] modules = r.toModuleArray();
        List<Movement> tunnelMoves = new ArrayList<Movement>();
        List<Movement> connectMoves = new ArrayList<Movement>();


        int x = 0;
        int y = 0;

        for (int i = 0; i < modules.length; i++) {
            for (int j = modules[0].length - 1; j >= 1; j--) {
                if (modules[i][j] != null) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        System.out.println(String.format("Moving %d, %d to %d, %d", x, y, x + 1, 0));

        Coordinate start = new Coordinate(x, y);
        Coordinate end = new Coordinate(x + 1, 0);
        tunnelMoves.add(ExpandedOneTunnel.initFromCoordsWithDir(r, dir, start, end));

        // connect all modules after wall moves
        connectMoves.add(new ConnectAll(r, true));

        //add to queue
        q.addLast(new ParallelMove(r, tunnelMoves));
        q.addLast(new ParallelMove (r, connectMoves));

    }

    @Override
    public ParallelMove determinePMove() {
    	if (q.isEmpty()) {
            enqueueMoves();
        }
        return q.pollFirst();
    }
}
