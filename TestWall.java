package src;

public class TestWall {

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

    /**
     * Inits and runs a simple combing test to check for syntax errors or
     * something really wrong
     */
    public static void combTestInDirWithBot(int dir, boolean[][] moduleBools) {
        String out = String.format("Testing in direction: %d", dir);
        System.out.println(out);
        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        
        r.drawUnit();
        boolean[][] temp = TestHelper.orientArray(moduleBools);
        Combing c = new Combing(temp, temp, false, dir);
    }

    public static void combTestDefault() {
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};
        for (int i = 0; i < 4; i++) {
            combTestInDirWithBot(i, moduleBools);
        }
    }

    public static void combTestCanSlide() {
        boolean[][] moduleBools = {{true, true , true },
                                   {true, false, false}};
        combTestInDirWithBot(2, moduleBools);
    }

    public static void combBase0() {
        boolean[][] moduleBools = {{true, true , true },
                                   {true, false, false}};
        for (int i = 0; i < 4; i++) {
            combTestInDirWithBot(i, moduleBools);
        }
    }

    public static void combHorseShoe() {
        int [][] m = {{1,1,0,1,1},
                      {1,0,0,0,1},
                      {1,1,1,1,1}};
        combTestInDirWithBot(2, TestHelper.convertIntToBool(m));
    }

    public static void main(String[] args) {
/*        makeWallTest1();*/
/*        combTestDefault();*/
/*        combTestCanSlide();*/
/*        combBase0();*/
        combHorseShow();
    }
}
