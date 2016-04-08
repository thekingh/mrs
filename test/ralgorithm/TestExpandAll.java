package ralgorithm;

import rutils.*;
import rgraph.*;

public class TestExpandAll {

    public static void testBroken() {
        int[][] s  = {{1,0,0,0},
                      {1,1,1,0},
                      {1,1,1,1}};
        Robot r = TestHelper.makeBot(s);
        ExpandAll e = new ExpandAll(r);
        TestHelper.runMove(e);
        r.drawUnit();
    }

	public static void main(String[] args) {
        testBroken();
	}
}
