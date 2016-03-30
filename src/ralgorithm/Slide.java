package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 * The Slide is a movement sliding one module along two adjacent modules.
 *
 *******************************************************************************
 * <p>Visual Aid: Modules are labeled with either a letter or number. Where A is
 *    the module of interest, and other modules are not part of the move, but important
 *    for handling connectedness and carrying.
 * <p>    
 * START <br>
 * 4     <br>
 * 0A3   <br>
 * 12    
 * <p>
 * END     <br>
 * &nbsp 4 <br>
 *      0A3<br>
 *      12
 * <p>
 * Requirements:
 * <ul>
 *      <li>1 exists iff 1 is connected to A and not connected to C</li>
 *      <li>if 2 exists and is connected to A, then it can slide with A</li>
 *      <li>if 0 exists and is connected to A, then it can slide with A</li>
 * </ul>
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class Slide implements Movement {
    private int currStep = 0;
    private static final int NUMSTEPS = 10;
    private final Robot r;

    private final Coordinate c;

    private final Module mA;
    private final Unit[] uA;

    private final int dir;
    private final int neighborDir;

    private final Module[] outerMs;
    private final Unit[][] outerUs;

    /**
     * Constructs a new slide movement given a module, a direction to slide, and
     * the direction of modules to slide on.
     *
     * @param  r             Robot to perform the slide on
     * @param  m             Module to slide
     * @param  dir           Direction to slide m
     * @param  neighborDir   Direction of neighbors to slide m on, i.e. direction
     *                       of B relative to A.
     * @throws RuntimeException given null module
     */
    public Slide(Robot r, Module m, int dir, int neighborDir) {
        this.r           = r;
        this.mA          = m;
        this.c = m.findSelfInArray(r.toModuleArray());
        this.uA          = mA.getUnitsFrom(neighborDir, dir);
        this.dir         = dir;
        this.neighborDir = neighborDir;

        if (m == null) {
            throw new RuntimeException("Trying to slide with null module");
        }


        outerMs = new Module[5];
        this.outerMs[1] = (Module) mA.getNeighbor(neighborDir);
        this.outerMs[2] = (Module) outerMs[1].getNeighbor(dir);

        outerUs = new Unit[7][4];
        for (int i = 0; i < outerMs.length; i++) {
            if (outerMs[i] != null) {
                outerUs[i] = outerMs[i].getUnitsFrom(neighborDir, dir);
            }
        }
    }

    public static Slide initFromCoord(Robot r, Coordinate c, int dir, int neighborDir) {
        return new Slide(r, r.toModuleArray()[c.x()][c.y()], dir, neighborDir);
    }

    /**
     * Performs slide operations in 10 steps.
     * <p>
     * Assumptions made:
     * <ol>
     *  <li>all modules are in a contracted state between move phases</li>
     *  <li>module size is 2</li>
     *  <li>dir and neighbor are adjacent directions</li>
     *  <li>B, C must have an edge between them</li>
     * <p>
     * Slides can be made in parallel on the same module
     * <p>Visual Aid: Units are labeled with either a letter or number. Where A is
     *    the module of interest, and other modules are not part of the move, but important
     *    for handling connectedness and carrying.
     * <p>
     * START<br>      
     * A2 A3<br>        
     * A1 A0<br>       
     * 12 13 22 23
     * <p>
     *
     * END<br>
     * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp     A2 A3 <br>
     * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp     A1 A0 <br>
     *                                        12 13 22 23 <br>
     */
    public void step() {
        if (currStep < 5) {
            performHalfSlide(outerUs[1][2], outerUs[1][3], outerUs[2][2], currStep % 5);
        } else{ 
            performHalfSlide(outerUs[1][3], outerUs[2][2], outerUs[2][3], currStep % 5);
        }
        currStep++;
    }

    public void finalize() {
        r.disconnectModules(mA, neighborDir);
        r.connectModules   (mA, outerMs[2], neighborDir);
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    //TODO IMPLEMENT
    public Movement invert(Robot s) {
        return Slide.initFromCoord(s, c.calcRelativeLoc(dir), 
            Direction.opposite(dir), neighborDir);
    }

    /**
     * performs half a contracted slide.
     * Note this redefines Aloupis' contracted slide by only moving the bottom
     * layer of the sliding moduel
     *
     * <p>Visual Aid: Units are labeled with either a letter or number. Where A is
     *    the module of interest, and other modules are not part of the move, but important
     *    for handling connectedness and carrying.
     * <p>    
     * START<br>
     * A2 A3<br>
     * A1 A0<br>
     * u1 u2 u3
     * <p>
     * END<br>
     * &nbsp&nbsp&nbsp&nbsp   A2 A3<br>
     * &nbsp&nbsp&nbsp&nbsp   A1 A0<br>
     *                     u1 u2 u3<br>
     */
    public void performHalfSlide(Unit u1, Unit u2, Unit u3, int step) {
        Unit trailing = uA[1];
        Unit leading  = uA[0];
    
        switch(step) {
            case 0:
                r.disconnect(uA[0], neighborDir);
                r.connect   (uA[1], u1, neighborDir);
                r.disconnect(uA[0], Direction.opposite(neighborDir));
                break;
            case 1:
                r.extend(uA[1], dir);
                break;
            case 2:
                r.connect   (uA[0], u3, neighborDir);
                r.disconnect(uA[1], neighborDir);
                break;
            case 3:
                r.contract  (uA[1], dir);
                break;
            case 4:
                r.connect   (uA[1], u2, neighborDir);
                r.connect   (uA[0], uA[3], Direction.opposite(neighborDir));
                break;
            default:
                throw new RuntimeException("Invalid step number in performing slide");
        }
    }

    public Robot getRobot() {
        return r;
    }
}
