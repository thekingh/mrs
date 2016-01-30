package src;

public class TestWall {
    public TestHelper t;

    public TestWall() {
/*        t = new TestHelper();*/
    }

    /**
     * Tests a wall by printing out the wall after construction and updating it once
     */
    public static void makeWallTest1() {
        
        int dir = 2;
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};

        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        r.drawUnit();
        Wall w = new Wall(r, 2);
        System.out.println(w);
        System.out.println("hasReachedEnd = " + w.hasReachedEnd());
        w.update(r);
        System.out.println(w);
        System.out.println("hasReachedEnd = " + w.hasReachedEnd());
    }

    public static void main(String[] args) {
        makeWallTest1();
    }
}
