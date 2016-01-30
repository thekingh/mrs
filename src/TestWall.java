package src;

public class TestWall {
    public TestHelper t;

    public TestWall() {
/*        t = new TestHelper();*/
    }

    public static void makeWallTest1() {
        
        int dir = 2;
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};

        
        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        r.drawUnit();
        Wall w = new Wall(r, 2);
    }

    public static void main() {
        makeWallTest1();
    }
}
