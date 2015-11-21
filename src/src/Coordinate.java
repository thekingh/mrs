package src;

public class Coordinate {
	private final Pair<Integer, Integer> coord;

	public Coordinate(Pair<Integer, Integer> coord) {
		this.coord = coord;
	}

	public Coordinate(int x, int y) {
		this.coord = new Pair<Integer, Integer>(x, y);
	}

	public int x() {
		return coord.a;
	}

	public int y() {
		return coors.y;
	}
}