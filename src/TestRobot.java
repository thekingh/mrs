package src;

public class TestRobot {
    private static void testConnectedness(Robot r, boolean connected) {
        assert r.isConnected() == connected;
        System.out.println("Test Passed");
    }

    public static void test1Bad() {
        int[][] s = {{1,0,1}};
        Robot r = TestHelper.makeBot(s);
        testConnectedness(r, false);
    }

    public static void test1Good() {
        int[][] s = {{1,1,1}};
        Robot r = TestHelper.makeBot(s);
        testConnectedness(r, true);
    }

    public static void test1Both() {
        int[][] s = {{1,1,1}};
        Robot r = TestHelper.makeBot(s);
        Module[][] ms = r.toModuleArray();
        testConnectedness(r, true);
        r.disconnect(ms[0][0], 1);
        testConnectedness(r, false);
    }

    public static void main(String[] args) {
        test1Bad();
        test1Good();
        test1Both();
    }
}
