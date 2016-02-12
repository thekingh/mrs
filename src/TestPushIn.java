package src;

public class TestPushIn {

    public static void runPushIn(Robot r, PushIn p) {
        //while (!p.reachedEnd()) {
        for (int i = 0; i < 29; i++) {
            System.out.println(i);
            p.step();
            r.drawUnit();
        }
    }

    public static void test1() {
        int[][] s = {{1,1},
                     {1,0}};
        Robot r = TestHelper.makeBot(s);

        Module[][] ms0 = r.toModuleArray();
        PushIn p0 = new PushIn(r, ms0[0][1], 2, 1);

        runPushIn(r, p0);
    }

    public static void main(String[] args) {
        test1();
    }

}
