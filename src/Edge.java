/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 * Senior Capstone Project
 *
 * Edge.java
 *
 * connection between neighboring robot modules
 */

package src;

public class Edge {
	private final Module[] endpoints;
	private boolean isConnected;
	private boolean isExtended;

	public Edge() {
		Edge(null, null, false, false);
	} 

	public Edge(Module m1, Module m2, boolean isConnected, boolean isExtended) {
		this.endpoints = new Module[2];
		this.endpoints[0] = m1;
		this.endpoints[1] = m2;
		this.isConnected = isConnected;
		this.isExtended = isExtended;
	}

	public boolean getIsConnected() {
	    return isConnected;
	}

	public void setIsConnected(boolean isConnected) {
	    this.isConnected = isConnected;
	}

	public boolean getIsExtended() {
	    return isExtended;
	}

	public void setIsExtended(boolean isExtended) {
	    this.isExtended = isExtended;
	}

}






