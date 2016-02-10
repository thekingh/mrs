package src;

import java.util.List;
import java.util.ArrayList;

public class KTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 10; //TODO ????

    public KTunnel() {
    	
    }

    public void step() {
        
        currStep++;
    }

    public boolean reachedEnd() {
        return currStep == NUMSTEPS;
    }

    /**
     * returns an opposite movement
     */
    public Movement invert() {
        return null;
    }

    public int opposite(int dir) {
        return (dir + 2) % 4;
    }
}
