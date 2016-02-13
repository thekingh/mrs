
package src;

import java.util.List;
import java.util.ArrayList;

public class Slide implements Movement {
    private int currStep = 0;
    private static final int NUMSTEPS = 10; //TODO ????
    private final Robot r;
    private final Module m;
    private final int dir;
    private final int neighborDir;
    private final Unit u1;
    private final Unit u2;
    private final Unit u3;
    private final Unit u4;
    private final Module m2;
    private final Module m3;

    public Slide(Robot r, Module m, int dir, int neighborDir) {
        this.r = r;
        this.m = m;
        this.dir = dir;
        this.neighborDir = neighborDir;
        if (m == null) {
            throw new RuntimeException("Trying to slide with null module");
        }

        m2 = (Module) m.getNeighbor(neighborDir);
        m3 = (Module) m2.getNeighbor(dir);
        u1 = m2.getUnitInQuadrant(Direction.opposite(neighborDir), Direction.opposite(dir));
        u2 = m2.getUnitInQuadrant(Direction.opposite(neighborDir), dir);
        u3 = m3.getUnitInQuadrant(Direction.opposite(neighborDir), Direction.opposite(dir));
        u4 = m3.getUnitInQuadrant(Direction.opposite(neighborDir), dir);
    }

    /**
     * Performs slide operations in 10 steps.
     * Assumptions made:
     * 1) all modules are in a contracted state between move phases
     * 2) module size is 2
     * 3) dir and neighbor are adjacent directions
     *
     * 1) must have edges between them (not necessarily connected)
     *
     * Slides can be made in parallel on the same module
     *
     * @param M             Module to slide
     * @param dir           Direction to slide module
     * @param neighborDir   Direction of neighboring modules to slide against
     */
    public void step() {
        if (currStep < 5) {
            performHalfSlide(u1, u2, u3, currStep);
        } else{ 
            performHalfSlide(u2, u3, u4, currStep);
        }
/*        r.drawUnit();*/
        currStep++;
    }

    public void finalize() {
        r.getModuleGraph().removeEdge(m.getEdge(neighborDir));
        r.getModuleGraph().addEdge(m, m3, neighborDir);
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    /**
     * returns an opposite movement
     */
    //TODO IMPLEMENT
    public Movement invert() {
        return null;
    }

    /**
     * performs half a contracted slide.
     * NOTE: This redefines Aloupis' contracted slide by only moving the bottom
     * layer of the sliding moduel
     */
    public void performHalfSlide(Unit u1, Unit u2, Unit u3, int step) {
        List<Unit> mSide = m.getSideUnits(neighborDir);
        Unit trailing = m.getUnitInQuadrant(neighborDir, Direction.opposite(dir));
        Unit leading  = m.getUnitInQuadrant(neighborDir, dir);
        Edge leadingEdge;
        Edge trailingEdge;
        
        //TODO REMOVE
        step = step % 5;
    
        switch(step) {
            case 0:
                leadingEdge = leading.getEdge(neighborDir);
                r.getUnitGraph().removeEdge(leadingEdge);
                r.getUnitGraph().addEdge(trailing, u1, neighborDir);
                leading.disconnect(Direction.opposite(neighborDir));
                break;
            case 1:
                trailing.extend(dir);
                break;
            case 2:
                trailingEdge = trailing.getEdge(neighborDir);
                r.getUnitGraph().addEdge(leading, u3, neighborDir);
                r.getUnitGraph().removeEdge(trailingEdge);
                break;
            case 3:
                trailing.contract(dir);
                break;
            case 4:
                r.getUnitGraph().addEdge(trailing, u2, neighborDir);
                leading.connect(Direction.opposite(neighborDir));
                break;
            default:
                System.out.println("OMG");
                break;
        }
    }

    public Robot getRobot() {
        return r;
    }
}
