/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 * robot module with n*n
 */

package src;

public class Module extends Node {

	private final int size;
	private Module inside;
	private Unit[][] units;

	public Module(int size) {
		super();
		this.size = size;
		this.units = new Unit[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.units[i][j] = new Unit();
			}
		}

		// TODO: make connections between sub units
        // potential to make a graph and call to grid and back?
	}

	public int getSize() {
		return size;
	}

	public Module getInside() {
		return inside;
	}

	public void putInside(Module m) {
		inside = m;
	}

	public boolean hasInside() {
		return (inside != null);
	}

	// NEEDSWORK: print all of the units?
	public String toString() {
		return "M";
	}
}
