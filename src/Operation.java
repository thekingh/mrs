
package src;

//TODO getters

/**
 * Operation is an operation performable on a robot.
 * Current valid Operations include: slide
 * 
 */
public class Operation {
    /**
     * takes a type of operation, a module to perform it on, and a direction
     * to perform it.
     * Note that the operation must be one that is performable in constant time
     *
     */
    private final int op;
    private final Module module;
    private final int dir;
    public Operation(String op, Module module, int dir) {
        int opnum = -1;
        if (op.equals("slide")) {
            opnum = 0;
        }
        /*
        if (opp.equals("pushin") {
            oppnum = 1;

        */
        if (opnum = -1) {
            System.out.println("Unrecognized Operation");
        }
    }

    public Operation(int op, Module module, int dir) {
        this.op = op;
        this.module = module;
        this.dir = dir;
    }
}
