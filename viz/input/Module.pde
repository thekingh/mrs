public class Module {
    Robot[] robots;
    int x; //NOTE THIS IS THE BOTTOM LEFT
    int y;

    public Module() {
        this(0, 0);
    }
    
    public Module(int x, int y) {
        this.x = x;
        this.y = y;

        robots   = new Robot[4];
        robots[0] = new Robot(this.x    , this.y);
        robots[1] = new Robot(this.x + 1, this.y);
        robots[2] = new Robot(this.x, this.y + 1);
        robots[3] = new Robot(this.x + 1, this.y + 1);
    }

    public void render() {
        for(Robot r: robots) {
            r.render();
        }
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }
 }
