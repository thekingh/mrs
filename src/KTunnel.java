package src;

import java.util.List;
import java.util.ArrayList;

public class KTunnel implements Movement {
	private int currStep = 0;
    private static final int NUMSTEPS = 10; //TODO ????

    // TODO: this is an implementation of a 1-tunnel, can be extended
    // to kTunnel with a list of coords in the kdirections
    public KTunnel(Robot r, Module m, int xDelta, int yDelta) {
    	Module[][] ms = r.toModuleArray();
        int mX;
        int mY;
        // finds location of m in ms
        for (int i = 0; i < ms.length; i++) {
            for (int j = 0; j < ms[0].length; j++) {
                if (ms[i][j].equals(m)) {
                    mX = i;
                    mY = j;
                    break;
                }
            }
        }

        // need to determine which direction to tunnel??


        // determine module to push in and direction
        // then determine which way to push out
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
