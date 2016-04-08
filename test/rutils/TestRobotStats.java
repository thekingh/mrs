package rutils;

import rutils.*;
import rgraph.*;
public class TestRobotStats {
    public static void printRobotStats(Robot r) {
        System.out.println(RobotStats.getAll(r));
    }

    public static void testIsExpanded() {
        int[][] start  = {{1,1,0},
                          {1,1,1}};
        Robot r = TestHelper.makeBot(start, true);
        RobotStats stats = new RobotStats(r);

        String ans = stats.getIsExpandedStat();
        assert(ans.equals("Robot is Expanded"));
    }

    public static void testIsExpandedEdges() {
        int[][] start  = {{1,1,0},
                          {1,1,1}};
        Robot r = TestHelper.makeBot(start);
        RobotStats stats = new RobotStats(r);

        String ans = stats.getModuleEdgeStat();
        System.out.println(ans);
    }


    public static void testIsNotExpanded() {
        int[][] start  = {{1,1,0},
                          {1,1,1}};
        Robot r = TestHelper.makeBot(start);
        RobotStats stats = new RobotStats(r);

        String ans = stats.getIsExpandedStat();
        assert(ans.equals("Robot is not Expanded"));
    }

    //BROKEN
    public static void testGetModuleEdgeStat() {
        int[][] start  = {{1,1,0},
                          {1,1,1}};
        Robot r = TestHelper.makeBot(start);
        RobotStats stats = new RobotStats(r);
        String ans = stats.getModuleEdgeStat();
        String expected = "Robot has 5 Module Edges";
        //assert (ans.equals(expected));
    }

    public static void main(String[] args) {
        testIsNotExpanded();
        testIsExpanded();
        testGetModuleEdgeStat();
        testIsExpandedEdges();
    }
}
