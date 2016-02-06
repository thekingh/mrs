
package src;


public class Slide implements Movement {
    private int currentStep = 0;
    private static final MAXSTEP = 9; //TODO ????
    private final Robot r;
    private final Module m;
    private final int dir;
    private final int neighborDir;

    public Slide(Robot r, Module m, int dir, int neighborDir) {
        this.r = r;
        this.m = m;
        this.dir = dir;
        this.neighborDir = neighborDir;
    }

    public void step() {


    }

    public void reachedEnd() {
        return currentStep == MAXSTEP;
    }

    /**
     * returns an opposite movement
     */
    //TODO IMPLEMENT
    public Movement invert() {
        return null;
    }




}
