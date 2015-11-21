package src;

enum GridObjectType {
	NODE,
	EDGE,
}

public class GridObject {
	private GridObjectType t; 
	private int id;

	public GridObject() {
		this(null, -1);
	}

	public GridObject(GridObjectType t, int id) {
		this.t  = t;
		this.id = id;
	}

	// public GridObjectType getType() {

		
	// }

}