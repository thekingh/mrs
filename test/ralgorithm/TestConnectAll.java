
package ralgorithm;

import rutils.*;
import rgraph.*;

public class TestConnectAll {

    public static void test1() {
        int[][] s = {{1,1,1},
    				 {1,1,1}};
        Robot r = TestHelper.makeBot(s);
        Module[][] ms = r.toModuleArray();

        r.disconnectModules(ms[0][1], 1);
        TestHelper.testConnectedness(r, false);

        ConnectAll c = new ConnectAll(r);
        TestHelper.runMove(c);
        TestHelper.testConnectedness(r, true);
    }

    public static void test2() {
        int[][] s = {{1,1,1},
    				 {1,1,1}};
        Robot r = TestHelper.makeBot(s);
        Module[][] ms = r.toModuleArray();

        Unit[] us1 = ms[0][1].getUnitsFrom(0,1);

        // disconnect top right unit of top left Module from unit to right (in diff module)
        r.disconnect(us1[0], 1);
        TestHelper.testConnectedness(r, false);

        ConnectAll c = new ConnectAll(r);
        TestHelper.runMove(c);
        TestHelper.testConnectedness(r, true);
    }

	public static void main(String[] args) {
		// test1();
		test2();
	}
}