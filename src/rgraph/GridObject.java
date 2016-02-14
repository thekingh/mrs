package rgraph;

import rutils.*;

/**
 * Represents an object at a 2D location.
 * <p>
 * GridObjects are final representations of objects associated with a coordinate
 * pair.
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
//TODO change to public objects?
public class GridObject {
    private final Object o;
    private final Coordinate c;
    
    public GridObject(Object o, Coordinate c) {
        this.o = o;
        this.c = c;
    }

    public Object o() {
    	return o;
    }

    public Coordinate c() {
    	return c;
    }

    public String toString() {
        return o.toString() + ", " + c.toString();
    }
}
