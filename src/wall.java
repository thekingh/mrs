package src;

public class Wall {
    private int level; // index of the wall
    private boolean isVertical;
    private int dir;
    private Set<Module> all;
    private Set<Module> moving;

    private boolean isVertical(dir) {
        return ((dir % 2) == 0);
    }

    /**
     * dir is the direction of melt, 
     */
    public Wall(Robot r, int dir) {
        this.dir = dir;
        this.isVertical = isVertical(dir);

        Module[][] moduleArray = r.toModuleArray();
        //TODO check which is first
        int w = moduleArray.length;
        int h = moduleArray[0].length;
        //there will always be something in every row and every column
        //therefore we only need to look in the final column
        switch (dir) {
            case 0:
                level = 
                break;
            case 1:
                break;
            case 2:
                level 
                break;
            case 3:
                break;
            default:
                break;
        }

        
        
         
     
    }
}
