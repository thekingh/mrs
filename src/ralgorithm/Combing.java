package ralgorithm;

import rgraph.*;
import rutils.*;

/**
 * Transforms a Source Robot, S, into a Target Robot, T, in O(n) parallel steps
 * <p>
 * Basic outline of Algorithm is as follows:
 * <ol>
 * <li>Melt S down and right </li>
 * <li>Tranform S to common comb through a series of kTunnels </li>
 * <li>Do a reverse transformation of T to common comb from
 * common comb </li></ol>
 * <p>
 * Combing Algorithm was created by Greg Aloupis, et. al.
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class Combing extends Algorithm {
    private int dir; //direction of melt

    /**
     * Init function for combing algorithm, note that robots must be in
     * [x][y] up, right positive direction.
     */
    public Combing(Robot s, Robot t) {
        super(r);
        List<Algorithm> parts = new ArrayList<Algorithm>();
        parts.add(new Melt(s, 2))
        parts.add(new CombtoLine(s, 2))

        List<Algorithm> reverseParts = new ArrayList<Algorithm>();
        reverseParts.add(new Melt(t, 2))
        reverseParts.add(new CombtoLine(t, 2))

        List<State> states = new ArrayList<State>();

        for ()

    }


    @Override
    public boolean isComplete() {

        return false;
    }

    @Override
    public ParallelMove determinePMove() {

    }


}
    
