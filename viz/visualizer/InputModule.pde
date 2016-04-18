/** 
 * Module class exclusively for the input visualiztion
 *
 *  @author Casey Gowrie
 *  @author Alex Tong
 *  @author Kabir Singh
 *  
 *  @version 1.0
 */


public class InputModule {
    private InputUnit[] units;
    private int x; //NOTE THIS IS THE BOTTOM LEFT
    private int y; //OF MODULE CLUSTER

    public InputModule() {
        this(0, 0);
    }
    
    /**
     * Given an x and y coordinate, construct a new module containing
     * four robots (w/ the x and y coordinates the bottom left corner
     * of the module's footprint
     *
     * @param x leftmost bound of module's footprint
     * @param y bottommost bound of module's footprint
     */
    public InputModule(int x, int y) {
        this.x = x;
        this.y = y;

        units    = new InputUnit[4];
        units[0] = new InputUnit(this.x    , this.y);
        units[1] = new InputUnit(this.x + 1, this.y);
        units[2] = new InputUnit(this.x, this.y + 1);
        units[3] = new InputUnit(this.x + 1, this.y + 1);
    } 

    /**
     * Render all individual units of a module
     */
    public void render(boolean cur) {
        for(InputUnit u: units) {
            u.render(cur);
        }
    }

    /**
     *  Return the leftmost bound of the module's footprint
     */
    public int X() {
        return x;
    }

    /**
     *  Return the bottommost bound of the module's footprint
     */
    public int Y() {
        return y;
    }
 }
