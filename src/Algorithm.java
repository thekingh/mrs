
package src;

public abstract class Algorithm {

    protected final boolean[][] inputRobot;
    protected final boolean[][] outputRobot;
    protected final int maxSteps;
    protected Robot currentState;
    protected int step;
    protected boolean success;

    public Algorithm() {
        inputRobot  = null;
        outputRobot = null;
        currentState = null;
        step = 0;
        success = false;
        maxSteps = 5000;
    }

    public Algorithm(boolean[][] in, boolean[][] out, boolean expanded) {
        inputRobot = in;
        outputRobot = out;
        currentState = new Robot(in, expanded);
        step = 0;
        success = false;
        maxSteps = 5000;
    }

    /**
     * determineParallelStep builds a queue of all opperations to perform in the
     * next parallel step.
     *
     * The algorithm for this function is as follows:
     *      determine which "part" of the combing algorithm we are on.
     */
    protected abstract ParallelStep determineParallelStep();

    public void performParallelStep(ParallelStep stepsToPerform) {

    }

    public void run() {
/*        for (int i = 0; i < maxSteps; i++) {*/
/*            ParallelStep steps = determineParallelStep();*/
/*            if (steps.peek() != null) {*/
/*                success = true;*/
/*                break;*/
/*            }*/
/*            performParallelStep(steps);*/
/*            step++;*/
/*        }*/
    }
}
