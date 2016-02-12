package src;

public class TestPushIn {

    public static void pushInTest(Robot r, int[][] finish, PushIn p) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("1Tunnel");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runAndDisplayMoveForSteps(r, p, 30);

        r.drawModule();
        TestHelper.validateOutput(r, f);
    }

    public static void test1() {
        int[][] s = {{1,1},
                     {1,0}};
        int[][] f = {{0,1},
                     {1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        PushIn p0 = new PushIn(r, ms0[0][1], 2, 1);

        pushInTest(r, f, p0);
    }

    // TODO: add new directions
    public static void test2() {
        // What should this look like??
        int[][] s = {{1,1,0},
                     {1,1,0},
                     {0,1,1}};
        int[][] f = {{1,0,0},
                     {1,1,1},
                     {0,1,1}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        PushIn p0 = new PushIn(r, ms0[1][1], 2, 1);

        pushInTest(r, f, p0);
    }

    public static void main(String[] args) {
        // test1();
        test2();
    }

}
