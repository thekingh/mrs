package src;

import java.lang.Math;

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
		return coord.b;
	}

	public String toString() {
		return String.format("(%d, %d)", x(), y());
	}

    /**
     * Calculates a new coordinate relative to an input coordinate
     * Note that coordinate system is x pos is right and y pos is up
     *
     * @param dir       The relative direction of the new point relative to the
     *                  current point note that dir is in the set {0,1,2,3},
     *                  where 0=North, 1=East, 2=South, 3=West.
     * @param ext       The distance between the new point and the current point
     *                  i.e. ext(ension) of 0 is adjacent
     * @return          The new absolute coordinate, returns null if invalid dir
     */
    public Coordinate calcRelativeLoc(int dir, int ext) {
        int x = x();
        int y = y();
        switch(dir) {
            case 0: //North
                y += 1 + ext;
                break;
            case 1: //East
                x += 1 + ext;
                break;
            case 2: //South
                y -= 1 + ext;
                break;
            case 3: //West
                x -= 1 + ext;
                break;
            default:
                return null;
        }
        return new Coordinate(x, y);
    }

    /**
     * Calculates Manhattan distance to another coordinate
     */
    public int mDist(Coordinate c) {
        return xDist(c) + yDist(c);
    }

    public int xDist(Coordinate c) {
        return Math.abs(x() - c.x());
    }

    public int yDist(Coordinate c) {
        return Math.abs(y() - c.y());
    }
}
