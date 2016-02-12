package src;

public class Wall {
    private int level; // index of the wall
    private boolean isDirVertical;
    private int dir;
    private Module[] wallModules;
    private Boolean[] isMoving;

    /* TODO different names? redeclare vs class vars?*/
    private int w;
    private int h;
    /**
     * dir is the direction of melt, 
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

    public int getHeight(Module[][] ms) {
        return ms[0].length;
    }
    public int getWidth(Module[][] ms) {
        return ms.length;
    }

    public boolean hasReachedEnd() {
        return isFinalEdge();
    }

    /**
     * marks as moving if a module can slide in a direction.
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
     */
    public String printWall() {
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

    public Module[] getWallModules() {
        return wallModules;
    }

    public Boolean[] getIsMoving() {
        return isMoving;
    }

    /**
     * Short Representation of Wall Object for Testing Purposes
     */
    public String toString() {
		String toReturn = "<Wall moving in direction: " + dir + " Level: " + level + ">\n";
        toReturn += "<" + printWall() + ">";
        return toReturn;
	}
}
