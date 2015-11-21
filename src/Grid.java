/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * grid representation of a robot
 */

package src;


public class Grid {

	//private Map<Integer, >  
	private Object[][] grid;
	private int h, w;

	public Grid() {
		this(0, 0);
	}

	public Grid(int h, int w) {
		this.h = h;
		this.w = w;

		grid = new GridObject[w][h];
		for(int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				grid[i][j] = null;
				// TODO gridobjects
			}
		}
	}

	public Graph toGraph() {
		return null;
	}




}



