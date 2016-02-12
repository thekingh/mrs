package src;

public final class Direction {
	// private final int dir;

	// public Direction(int dir) {
	// 	this.dir = dir;
	// }

	// public int getDir() {
	// 	return dir;
	// }

	public static boolean isVertical(int dir) {
		return (dir % 2) == 0;
	}

	public static int opposite(int dir) {
		return (dir + 2) % 4;
	}

	public static int right(int dir) {
		return (dir + 1) % 4;
	}

	public static int left(int dir) {
		return (dir - 1 + 4) % 4;
	}

	// public int hashCode() {
	// 	Integer d = dir;
	// 	return d.hashCode();
	// }

	// public boolean equals(Direction o) {
	// 	return dir == o.getDir();
	// }

	// public boolean equals(int d) {
	// 	return dir == d;
	// }

	// public String toString() {
	// 	return Integer.toString(dir);
	// }
}