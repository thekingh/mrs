package src;

public class GridObject {
	// if we need this then we could have edge and node and one be null
	// then two constructors for it one with edge one with node
    //
    public final Object o;
    public final Coordinate c;
    
    public GridObject(Object o, Coordinate c) {
        this.o = o;
        this.c = c;
    }

    public String toString() {
        return o.toString() + ", " + c.toString();
    }
}
