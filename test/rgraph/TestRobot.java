package rgraph;

import rutils.*;

public class TestRobot {

    public static void test1Bad() {
        int[][] s = {{1,0,1}};
        Robot r = TestHelper.makeBot(s);
        TestHelper.testConnectedness(r, false);
    }

    public static void test1Good() {
        int[][] s = {{1,1,1}};
        Robot r = TestHelper.makeBot(s);
        TestHelper.testConnectedness(r, true);
    }

    public static void test1Both() {
        int[][] s = {{1,1,1}};
        Robot r = TestHelper.makeBot(s);
        Module[][] ms = r.toModuleArray();
        TestHelper.testConnectedness(r, true);
        r.disconnectModules(ms[0][0], 1);
        TestHelper.testConnectedness(r, false);
    }

    public static void main(String[] args) {
        test1Bad();
        test1Good();
        test1Both();
    }
}
