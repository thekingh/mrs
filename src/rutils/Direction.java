package rutils;

/**
 * Provides static operations of integers, which act as Directions
 * <p>
 * 0 (up), 1 (right), 2 (down), 3 (left) are valid directions
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/16
 */
public final class Direction {
	/**
	 * The number of directions
	 */
    public static final int NUM_DIR = 4;

    /**
     * @return True is the direction is vertical (up or down)
     */
	public static boolean isVertical(int dir) {
		return (dir % 2) == 0;
	}

	/**
	 * @param dir direction to which opposite is desired
	 * @return an int representation of the direction accross from dir
	 */
	public static int opposite(int dir) {
		return (dir + NUM_DIR / 2) % NUM_DIR;
	}

	/**
	 * @param dir direction to which right is desired
	 * @return an int representation of the direction right of dir
	 */
	public static int right(int dir) {
		return (dir + 1) % NUM_DIR;
	}

	/**
	 * @param dir direction to which left is desired
	 * @return an int representation of the direction left from dir
	 */
	public static int left(int dir) {
		return (dir - 1 + NUM_DIR) % NUM_DIR;
	}
}
