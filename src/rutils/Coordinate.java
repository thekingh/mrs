package rutils;

import java.lang.Math;

public class Coordinate {
    private final int x;
    private final int y;

    // TODO do we use this constructor?
	public Coordinate(Pair<Integer, Integer> coord) {
        this.x = coord.a;
        this.y = coord.b;
	}

	public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
	}

	public int x() {
        return x;
	}

	public int y() {
        return y;
	}

	public String toString() {
		return String.format("(%d, %d)", x(), y());
	}

    public boolean inBounds(int w, int h) {
        return (x() >= 0) && (x() < w) && (y() >= 0) && (y() < h);
    }

    public Coordinate calcRelativeLoc(int dir) {
        return calcRelativeLoc(dir, 0);
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
