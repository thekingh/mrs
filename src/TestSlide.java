
package src;

public class TestSlide {
    public static void test1() {
        boolean[][] moduleBools = {{true, false},
                                   {true, true }};
        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        r.drawUnit();

        Module[][] ms = r.toModuleArray();


        Slide s = new Slide(r, ms[0][1], 1, 2);
        runSlide(s);
    }

    public static void test2() {
        int [][] m = {{1,1,0,1,1},
                      {1,0,0,0,1},
                      {1,1,1,1,1}};
        boolean[][] moduleBools = TestHelper.convertIntToBool(m);
        Robot r = new Robot(TestHelper.orientArray(moduleBools), false);
        r.drawUnit();

        Module[][] ms = r.toModuleArray();

        Slide s1 = new Slide(r, ms[1][2], 2, 3);
        runSlide(s1);
        Slide s2 = new Slide(r, ms[3][2], 2, 1);
        runSlide(s2);
    }

    public static void runSlide(Slide s) {
        while (!s.reachedEnd()) {
            s.step();
        }
    }


    public static void main(String[] args) {
/*        test1();*/
        test2();

    }

}
