package src;

public class TestWall {
    public TestHelper t;

    public TestWall() {
        t = new TestHelper();
    }

    public static void makeWallTest1() {
        
        int dir = 2;
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};

        r.drawUnit();
        
        Robot r = new Robot(t.orientArray(moduleBools), false);
        Wall w = new Wall(r, 2);
    }

    public static void main() {
        makeWallTest1();
    }
}
