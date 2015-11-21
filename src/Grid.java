/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * grid representation of a robot
 */

package src;

import java.util.Map;


public class Grid {

	// map from node id to coordinate
	private Map<Integer, Coordinate> nodes;  
	private Object[][] grid;
	private int h, w;

	public Grid() {
		h = 0;
		w = 0;
	}

}



