package ralgorithm;

import rutils.*;
import rgraph.*;

public class TestMakeTunnel{
    public static void slideTest(Robot r, int[][] finish, Slide s) {
        Robot f = TestHelper.makeBot(finish);

        System.out.println("=====================================");
        System.out.println("Sliding");
        r.drawModule();
        System.out.println("=====================================");

        TestHelper.runAndDisplayMove(s);

        r.drawModule();
        TestHelper.validateOutput(r, f);
    }
}
