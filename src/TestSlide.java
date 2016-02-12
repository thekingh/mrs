
package src;

public class TestSlide {


    public static void slideTest(Robot r, int[][] finish, Slide s) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Sliding");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runMove(s);

        r.drawModule();
        TestHelper.validateOutput(r, f);
    }


    public static void test1() {
        int [][] s = {{1,0,0},
                      {1,1,1}};
        int [][] f0 = {{0,1,0},
                       {1,1,1}};
        int [][] f1 = {{0,0,1},
                       {1,1,1}};

        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        Slide s0 = new Slide(r, ms0[0][1], 1, 2);

        slideTest(r, f0, s0);

        Module[][] ms1 = r.toModuleArray();
        Slide s1 = new Slide(r, ms1[1][1], 1, 2);

        slideTest(r, f1, s1);

    }

    public static void test2() {
        int [][] s = {{1,1,0,1,1},
                      {1,0,0,0,1},
                      {1,1,1,1,1}};
        int [][] f0 = {{1,0,0,1,1},
                       {1,1,0,0,1},
                       {1,1,1,1,1}};
        int [][] f1 = {{1,0,0,0,1},
                       {1,1,0,1,1},
                       {1,1,1,1,1}};

        Robot r = TestHelper.makeBot(s);

        Module[][] ms = r.toModuleArray();
        Slide s1 = new Slide(r, ms[1][2], 2, 3);
        slideTest(r, f0, s1);
        Slide s2 = new Slide(r, ms[3][2], 2, 1);
        slideTest(r, f1, s2);
    }


    public static void main(String[] args) {
        test1();
        test2();
    }

}
