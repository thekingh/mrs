package src;

public class GridObject {
	// if we need this then we could have edge and node and one be null
	// then two constructors for it one with edge one with node
    //
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
