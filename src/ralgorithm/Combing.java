package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

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
    private LinkedList<State> states;

    /**
     * Init function for combing algorithm, note that robots must be in
     * [x][y] up, right positive direction.
     */
    public Combing(Robot s, Robot t) {
        super(s);
        
        List<Algorithm> parts = new ArrayList<Algorithm>();
        parts.add(new Melt(s, 2));
        parts.add(new CombToLine(s, 2));

        List<Algorithm> reverseParts = new ArrayList<Algorithm>();
        reverseParts.add(new Melt(t, 2));
        reverseParts.add(new CombToLine(t, 2));

        states = new LinkedList<State>();
        for (Algorithm part : parts) {
            states.addAll(part.run());
        }
        for (Algorithm reversePart : reverseParts) {
            states.addAll(reversePart.runReverse());
        }
    }


    @Override
    public boolean isComplete() {
        return states.isEmpty();
    }

    @Override
    public List<State> determineNextStates() {
        List s = new ArrayList<State>();
        s.add(states.pollFirst());
        return s;
    }


}
    
