package ralgorithm;

import rgraph.*;
import rutils.*;

/**
 * Wall class that represents a level of Modules in a robot
 * <p>
 * The wall is used to move in a direction, while bringing Modules that
 * can be moved in that direction with it, as it updates.
 * <p>
 * An example of a Wall level can be seen as follows:
 * <p>
 * ABCD<br>
 * EF GH 
 * <p> With the above robot if the wall was at level 1, with dir 2 (down)
 * the wall would consist of modules {A,B,C,D,null} and then we could
 * mark the moving modules as {false,false,true,false,false} because C
 * is the only Module that can move down with the wall. 
 * One could then use this information to slide Module C down and update
 * the wall
 *  
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/2016
 */
public class Wall {
    // TODO: make Robot part of class? or want to update it every time??
    private int level; // index of the wall
    private boolean isDirVertical;
    private int dir;
    private Module[] wallModules;
    private Boolean[] isMoving;

    /* TODO different names? redeclare vs class vars?*/
    private int w;
    private int h;

    /**
     * Instantiates a wall class on a robot with a wall that can move in a direction
     * 
     * @param r the Robot on which the wall will be setup
     * @param dir the Direction the wall can move 
     */
    public Wall(Robot r, int dir) {
        this.dir = dir;
        this.isDirVertical = Direction.isVertical(dir);

        //2D arrays are width height
        Module[][] moduleArray = r.toModuleArray();
        //TODO check which is first
        w = getWidth(moduleArray);
        h = getHeight(moduleArray);
        //there will always be something in every row and every column
        //therefore we only need to look in the final column
        switch (dir) {
            case 0:
            case 2:
                level = h;
                break;
            case 1:
            case 3:
                level = w;
                break;
            default:
/*                throw new RuntimeException("Invalid Wall direction");*/
                break;
        }
        /* TODO possibly duplicate code */
        int size = isDirVertical ? w : h;
        wallModules = new Module[size];
        isMoving = new Boolean[size];
    }

    private int getHeight(Module[][] ms) {
        return ms[0].length;
    }
    private int getWidth(Module[][] ms) {
        return ms.length;
    }

    /**
     * @return True if the wall has been moved all the way in one direction
     */
    public boolean hasReachedEnd() {
        return isFinalEdge();
    }

    /**
     * marks as moving if a module can slide in a direction.
     *
     * Note that at the last row/col in the direction of movement, none of the
     * modules can slide.
     */
    private void markMoving(boolean hasReachedEnd) {
        for (int i = 0; i < wallModules.length; i++) {
            if (wallModules[i] == null) {
                isMoving[i] = null;
            } else {
                isMoving[i] = !hasReachedEnd && !wallModules[i].hasNeighborInDirection(dir);
            }
        }
    }

    /**
     * keeps track of where in the Robot the wall is?
     */
    private int getWallIndex() {
        switch(dir) {
            case 0:
                return h - level - 1;
            case 1:
                return w - level - 1;
            case 2:
            case 3:
                return level;
            default:
                return -1;
        }
    }
    
    /**
     * populates wall module array (class variable, with modules of the robot
     */
    private void populateWall(Module[][] moduleArray) {
        int j = getWallIndex();
        if (isDirVertical) {
            for(int i = 0; i < w; i++ ) {
                wallModules[i] = moduleArray[i][j];
            }
        } else {
            for(int i = 0; i < h; i++ ) {
                wallModules[i] = moduleArray[j][i];
            }
        }
    }

    private boolean isFinalEdge() {
        return level == 0;
    }

    /**
     * Updates the robot by moving the wall down and recalculating Wall
     *
     * @param r Robot which wall is in
     * @return True if update was successful
     */
    public boolean update(Robot r) {
        Module[][] moduleArray = r.toModuleArray();
        w = getWidth(moduleArray);
        h = getHeight(moduleArray);
        
        int size = isDirVertical ? w : h;
        wallModules = new Module[size];
        isMoving = new Boolean[size];
        
        level--;
        populateWall(moduleArray); //populates module Array
        markMoving(isFinalEdge()); //populates ismoving
        
        return true;
    }

    /**
     * debug method to show which modules are moving, stationary, or non existant
     *
     * @return Long String representation of Movement in Wall
     */
    private String printWall() {
        String toReturn = "";
        for (int i = 0; i < wallModules.length; i++) {
            if (wallModules[i] != null) {
                if (isMoving[i]) {
                    toReturn += "M";
                } else {
                    toReturn += "S";
                }
            } else {
                toReturn += " ";
            }
        }
        return toReturn;
    }

    /**
     * Gets an array of Modules in the wall
     *
     * @return the Module array for the wall (or null if no Module exists at index)
     */
    public Module[] getWallModules() {
        return wallModules;
    }

    /**
     * Gets boolean array with information on which Modules can move
     *
     * @return Boolean array with null where no Moduel exists
     */
    // NEEDSWORK: do we want null or fale where no module exists
    public Boolean[] getIsMoving() {
        return isMoving;
    }

    /**
     * Short Representation of Wall Object for Testing Purposes
     *
     * @return String representation of wall
     */
    public String toString() {
		String toReturn = "<Wall moving in direction: " + dir + " Level: " + level + ">\n";
        toReturn += "<" + printWall() + ">";
        return toReturn;
	}
}
