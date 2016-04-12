package ralgorithm;

import rgraph.*;
import rutils.*;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016 
 */
public class Staircase implements Movement {
    private final static int NUM_STEPS = 69;

    private final Robot r;
    private final Module mc;
    private final Coordinate c;
    private final int k1;
    private final int k2;
    private int currStep;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private final List<Movement> makeTunnelAMovesEven;
    private final List<Movement> makeTunnelAMovesOdd;
    private final List<Movement> stepAMovesEven;
    private final List<Movement> stepAMovesOdd;
    private ParallelMove makeTunnelAPMoveEven;
    private ParallelMove makeTunnelAPMoveOdd;
    private ParallelMove stepAPMoveEven;
    private ParallelMove stepAPMoveOdd;

    private final List<Movement> makeTunnelBMovesEven;
    private final List<Movement> makeTunnelBMovesOdd;
    private final List<Movement> stepBMovesEven;
    private final List<Movement> stepBMovesOdd;
    private ParallelMove makeTunnelBPMoveEven;
    private ParallelMove makeTunnelBPMoveOdd;
    private ParallelMove stepBPMoveEven;
    private ParallelMove stepBPMoveOdd;

    private final List<Movement> makeTunnelCMovesEven;
    private final List<Movement> makeTunnelCMovesOdd;
    private final List<Movement> stepCMovesEven;
    private final List<Movement> stepCMovesOdd;
    private ParallelMove makeTunnelCPMoveEven;
    private ParallelMove makeTunnelCPMoveOdd;
    private ParallelMove stepCPMoveEven;
    private ParallelMove stepCPMoveOdd;

    // private final ParallelMove stepA;
    // private final ParallelMove stepB;
    // private final ParallelMove stepC;



    /**
     * @param k1 size of box in y
     * @param k2 size of box in x
     */
    public Staircase(Robot r, Module mc, int k1, int k2) {
        this.r = r;
        this.mc = mc;
        this.c = mc.findSelfInArray(r.toModuleArray());
        this.k1 = k1;
        this.k2 = k2;
        this.currStep = 0;

        x1 = c.x();
        y1 = c.y();
        x2 = x1 + k2;
        y2 = y1 + k1;

        System.out.println(String.format("Box %d, %d to %d, %d", x1, y1, x2, y2));

        /////////////////////////////////////////////////////////////////////
        /////////////////// Phase A /////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        makeTunnelAMovesEven = new ArrayList<Movement>();
        makeTunnelAMovesOdd = new ArrayList<Movement>();
        stepAMovesEven = new ArrayList<Movement>();
        stepAMovesOdd = new ArrayList<Movement>();

        /////////////////////////////////////////////////////////////////////
        /////////////////// Phase B /////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        makeTunnelBMovesEven = new ArrayList<Movement>();
        makeTunnelBMovesOdd = new ArrayList<Movement>();
        stepBMovesEven = new ArrayList<Movement>();
        stepBMovesOdd = new ArrayList<Movement>();

        /////////////////////////////////////////////////////////////////////
        /////////////////// Phase C /////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        makeTunnelCMovesEven = new ArrayList<Movement>();
        makeTunnelCMovesOdd = new ArrayList<Movement>();
        stepCMovesEven = new ArrayList<Movement>();
        stepCMovesOdd = new ArrayList<Movement>();

    }

    /**
     * Run a single step of the movement.
     * <p>
     * Number of steps is tracked in implementation
     */
    public void step() {

        System.out.println(String.format("Step %d", currStep));
        int i =0;
        // TODO: broken because can't slide like we are, must make tunnel,
        // or write expanded slide
        // TODO: do all in parallel
        switch (currStep) {
            /////////////////////////////////////////////////////////////////////
            /////////////////// Phase A /////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            case 0:
                for (int j = y1 + 1; j < y2; j++) {
                    if (j % 2 == 0) {
                        makeTunnelAMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
                            1, 2));
                        stepAMovesEven.add(Slide.initFromCoord(r, new Coordinate(x1, j),
                            1, 2));
                    } else {
                        makeTunnelAMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
                            1, 2));
                        stepAMovesOdd.add(Slide.initFromCoord(r, new Coordinate(x1, j),
                            1, 2));
                    }
                }
                makeTunnelAPMoveEven = new ParallelMove(r, makeTunnelAMovesEven);
                makeTunnelAPMoveOdd = new ParallelMove(r, makeTunnelAMovesOdd);
                stepAPMoveEven = new ParallelMove(r, stepAMovesEven);
                stepAPMoveOdd = new ParallelMove(r, stepAMovesOdd);

                makeTunnelAPMoveEven.step();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                stepAPMoveEven.step();
                break;
            case 11:
                makeTunnelAPMoveOdd.step();
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                stepAPMoveOdd.step();
                break;
            case 22:
                (new ConnectAll(r)).step();

            /////////////////////////////////////////////////////////////////////
            /////////////////// Phase B /////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            case 23:
                i = x2;
                // NOTE: typo in his research bc we need to slide down one column that touches border
                for (int j = y1 + 1; j < y2 - k2 + 1; j++) {
                    // make tunnel up
                    // slide down
                    if (j % 2 == 0) {
                        makeTunnelBMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(i, j),
                            0, 3));
                        stepBMovesEven.add(Slide.initFromCoord(r, new Coordinate(i, j),
                            2, 3));
                    } else {
                        makeTunnelBMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(i, j),
                            0, 3));
                        stepBMovesOdd.add(Slide.initFromCoord(r, new Coordinate(i, j),
                            2, 3));
                    }
                    i++;
                }
                makeTunnelBPMoveEven = new ParallelMove(r, makeTunnelBMovesEven);
                makeTunnelBPMoveOdd = new ParallelMove(r, makeTunnelBMovesOdd);
                stepBPMoveEven = new ParallelMove(r, stepBMovesEven);
                stepBPMoveOdd = new ParallelMove(r, stepBMovesOdd);
                
                makeTunnelBPMoveEven.step();
                break;
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                stepBPMoveEven.step();
                break;
            case 34:
                makeTunnelBPMoveOdd.step();
                break;
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                stepBPMoveOdd.step();
                break;
            case 45:
                (new ConnectAll(r)).step();


            /////////////////////////////////////////////////////////////////////
            /////////////////// Phase C /////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            case 46:
                i = x1 + 1;
                // NOTE: typo in his research bc we need to slide down one column that touches border
                for (int j = y1 + 1; j < y1 + k2; j++) {
                    // tunnel to right
                    // slide left
                    if (j % 2 == 0) {
                        makeTunnelCMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(i, j),
                            1, 2));
                        stepCMovesEven.add(Slide.initFromCoord(r, new Coordinate(i, j),
                            3, 2));
                    } else {
                        makeTunnelCMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(i, j),
                            1, 2));
                        stepCMovesOdd.add(Slide.initFromCoord(r, new Coordinate(i, j),
                            3, 2));
                    }
                    i++;
                }
                makeTunnelCPMoveEven = new ParallelMove(r, makeTunnelCMovesEven);
                makeTunnelCPMoveOdd = new ParallelMove(r, makeTunnelCMovesOdd);
                stepCPMoveEven = new ParallelMove(r, stepCMovesEven);
                stepCPMoveOdd = new ParallelMove(r, stepCMovesOdd);

                makeTunnelCPMoveEven.step();
                break;
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                stepCPMoveEven.step();
                break;
            case 57:
                makeTunnelCPMoveOdd.step();
                break;
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                stepCPMoveOdd.step();
                break;
            case 68:
                (new ConnectAll(r)).step();

        }
        
        currStep++;




                // if (makeTunnelAPMoveEven == null) {
        //     for (int j = y1 + 1; j < y2; j++) {
        //         if (j % 2 == 0) {
        //             makeTunnelAMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepAMovesEven.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         } else {
        //             makeTunnelAMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepAMovesOdd.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         }
        //     }
        //     makeTunnelAPMoveEven = new ParallelMove(r, makeTunnelAMovesEven);
        //     makeTunnelAPMoveOdd = new ParallelMove(r, makeTunnelAMovesOdd);
        //     stepAPMoveEven = new ParallelMove(r, stepAMovesEven);
        //     stepAPMoveOdd = new ParallelMove(r, stepAMovesOdd);

        //     makeTunnelAPMoveEven.step();
        // } else if (makeTunnelAPMoveEven.isComplete() && !stepAPMoveEven.isComplete()) {
        //     stepAPMoveEven.step();
        // } else if (stepAPMoveEven.isComplete() && !makeTunnelAPMoveOdd.isComplete()) {
        //     makeTunnelAPMoveOdd.step();
        // } else if (makeTunnelAPMoveOdd.isComplete() && !stepAPMoveOdd.isComplete()) {
        //     stepAPMoveOdd.step();
        // } else if (stepAPMoveOdd.isComplete() && makeTunnelBPMoveEven == null) {
        //     for (int j = y1 + 1; j < y2; j++) {
        //         if (j % 2 == 0) {
        //             makeTunnelBMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepBMovesEven.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         } else {
        //             makeTunnelBMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepBMovesOdd.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         }
        //     }
        //     makeTunnelBPMoveEven = new ParallelMove(r, makeTunnelBMovesEven);
        //     makeTunnelBPMoveOdd = new ParallelMove(r, makeTunnelBMovesOdd);
        //     stepBPMoveEven = new ParallelMove(r, stepBMovesEven);
        //     stepBPMoveOdd = new ParallelMove(r, stepBMovesOdd);

        //     makeTunnelBPMoveEven.step();
        // } else if (makeTunnelBPMoveEven.isComplete() && !stepBPMoveEven.isComplete()) {
        //     stepBPMoveEven.step();
        // } else if (stepBPMoveEven.isComplete() && !makeTunnelBPMoveOdd.isComplete()) {
        //     makeTunnelBPMoveOdd.step();
        // } else if (makeTunnelBPMoveOdd.isComplete() && !stepBPMoveOdd.isComplete()) {
        //     stepBPMoveOdd.step();
        // } else if (stepBPMoveOdd.isComplete() && makeTunnelCPMoveEven == null) {
        //     for (int j = y1 + 1; j < y2; j++) {
        //         if (j % 2 == 0) {
        //             makeTunnelCMovesEven.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepCMovesEven.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         } else {
        //             makeTunnelCMovesOdd.add(MakeTunnel.initFromCoords(r, new Coordinate(x1, j),
        //                 1, 2));
        //             stepCMovesOdd.add(Slide.initFromCoord(r, new Coordinate(x1, j),
        //                 1, 2));
        //         }
        //     }
        //     makeTunnelCPMoveEven = new ParallelMove(r, makeTunnelCMovesEven);
        //     makeTunnelCPMoveOdd = new ParallelMove(r, makeTunnelCMovesOdd);
        //     stepCPMoveEven = new ParallelMove(r, stepCMovesEven);
        //     stepCPMoveOdd = new ParallelMove(r, stepCMovesOdd);

        //     makeTunnelCPMoveEven.step();
        // } else if (makeTunnelCPMoveEven.isComplete() && !stepCPMoveEven.isComplete()) {
        //     stepCPMoveEven.step();
        // } else if (stepCPMoveEven.isComplete() && !makeTunnelCPMoveOdd.isComplete()) {
        //     makeTunnelCPMoveOdd.step();
        // } else if (makeTunnelCPMoveOdd.isComplete() && !stepCPMoveOdd.isComplete()) {
        //     stepCPMoveOdd.step();
        // }
    }


    /**
     * Determines if the Movement has completed all steps
     * 
     * @return True if done
     */
    public boolean reachedEnd() {
        // TODO: change
        return currStep >= NUM_STEPS;
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