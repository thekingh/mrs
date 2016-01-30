package src;

public class Wall {
    private int level; // index of the wall
    private boolean isDirVertical;
    private int dir;
    private Module[] wallModules;
    private Boolean[] isMoving;
    private boolean hasReachedEnd;

    /* TODO different names? redeclare vs class vars?*/
    private int w;
    private int h;
    /**
     * dir is the direction of melt, 
     */
    public Wall(Robot r, int dir) {
        this.dir = dir;
        this.isDirVertical = ((dir % 2) == 0);

        //2D arrays are width height
        Module[][] moduleArray = r.toModuleArray();
        //TODO check which is first
        w = moduleArray.length;
        h = moduleArray[0].length;
        //there will always be something in every row and every column
        //therefore we only need to look in the final column
        switch (dir) {
            case 0:
                level = 0; 
                break;
            case 1:
                level = 0;
                break;
            case 2:
                level = h - 1;
                break;
            case 3:
                level = w - 1;
                break;
            default:
/*                throw new RuntimeException("Invalid Wall direction");*/
                break;
        }
        /* TODO possibly duplicate code */
        int size = isDirVertical ? w : h;
        wallModules = new Module[size];
        isMoving = new Boolean[size];
        populateWall(moduleArray);
        markMoving();
    }

    public boolean hasReachedEnd() {
        return this.hasReachedEnd;
    }

    /**
     * marks as moving if a module can slide in a direction.
     */
    private void markMoving() {
        for (int i = 0; i < wallModules.length; i++) {
            if (wallModules[i] == null) {
                isMoving[i] = null;
            } else {
                isMoving[i] = wallModules[i].canSlide(dir);
            }
        }
    }
    /**
     * updates level number dependent on direction of melt
     */
    private int updateLevel() {
        switch (dir) {
            case 0:
            case 1:
                level++;
                break;
            case 2:
            case 3:
                level--;
                break;
            default:
/*                throw new RuntimeException("Invalid Wall direction");*/
                break;
        }

        return level;
    }
    
    private void populateWall(Module[][] moduleArray) {
        if (isDirVertical) {
            for(int i = 0; i < w; i++ ) {
                wallModules[i] = moduleArray[i][level];
            }
        } else {
            for(int i = 0; i < h; i++ ) {
                wallModules[i] = moduleArray[level][i];
            }
        }
    }

    /**
     * checks if the wall is at one of the edges of the robot, without regard
     * to direction travelled.
     * TODO UNTESTED
     */
    private boolean isEdgeWall() {
        if (level == 0) {
            return true;
        }
        if (isDirVertical) {
            return (level == h - 1);
        } else {
            return (level == w - 1);
        }
    }

    public boolean update(Robot r) {
        if (hasReachedEnd) {
            return false;
        }
        Module[][] moduleArray = r.toModuleArray();
        int size = isDirVertical ? w : h;
        wallModules = new Module[size];
        isMoving = new Boolean[size];

        level = updateLevel(); 

        populateWall(moduleArray); 
        markMoving();
        
        //check if this is the last possible update, update hasreachedend
        hasReachedEnd = isEdgeWall();
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

    /**
     * Short Representation of Wall Object for Testing Purposes
     */
    public String toString() {
		String toReturn = "<Wall moving in direction: " + dir + " Level: " + level + ">\n";
        toReturn += "<" + printWall() + ">";
        return toReturn;
	}
}
