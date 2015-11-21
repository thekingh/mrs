/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * grid representation of a robot
 */

package src;


public class Grid {

	// map from node id to coordinate
	private Map<Integer, Coordinate> nodes;  
	private Object[][] grid;
	private int h, w;

	public Grid() {
		this(0, 0);
	}

}



