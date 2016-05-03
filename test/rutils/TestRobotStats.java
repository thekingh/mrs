package rutils;

import rutils.*;
import rgraph.*;
/**
 * The Test Robot Stats class prints out helpful statistics about a given Robot
 * Object. 
 * <p>
 * In testing robots we found it helpful to find a number of pieces of information
 * about a robot in consecuative steps. This class helps to find useful pieces
 * of information about a robot and print them to the command line for debugging
 * purposes.
 *
 * <p> 
 * All functions are provided as public for debugging purposes, this class is meant
 * to be very extensible in allowing for additional functions to be added when
 * statistics are printed. 
 */
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
