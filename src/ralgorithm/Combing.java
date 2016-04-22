package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Transforms a Source Robot, S, into a Target Robot, T, in O(n) parallel steps
 * <p>
 * Basic outline of Algorithm is as follows:
 * <ol>
 * <li>Melt S down </li>
 * <li>Tranform S to line through a series of oneTunnels </li>
 * <li>Do a reverse transformation of T to target from combed from
 * line </li></ol>
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
     * <p>
     * Initializer enqueues all moves in the full tranformation and then
     * q gets polled for states
     */
    public Combing(Robot s, Robot t) {
        super(s);
        
        List<Algorithm> parts = new ArrayList<Algorithm>();
        // melt and comb S
        parts.add(new Melt(s, 2));
        parts.add(new CombToLine(s, 2));

        List<Algorithm> reverseParts = new ArrayList<Algorithm>();
        // melt and comb t
        reverseParts.add(new Melt(t, 2));
        reverseParts.add(new CombToLine(t, 2));

        states = new LinkedList<State>();
        LinkedList<State> fstates = new LinkedList<State>();
        LinkedList<State> rstates = new LinkedList<State>();
        fstates.add(new State(s));
        for (Algorithm part : parts) {
            fstates.addAll(part.run());
        }
        // reverse tranformation of t
        //
        rstates.add(new State(t));
        for (Algorithm reversePart : reverseParts) {
            rstates.addAll(reversePart.run());
        }
        states.addAll(fstates);
        Collections.reverse(rstates);
        states.addAll(rstates);
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
    
