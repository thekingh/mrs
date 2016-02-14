package src;

import java.util.HashSet;


public class EdgeTest {
    public static void main (String[] args) {
        
        HashSet<Edge> edgeSet = new HashSet<Edge>();


        Node n1 = new Unit();
        Node n2 = new Unit();
        Node n3 = new Unit();
        Node n4 = new Unit();
        Node n5 = new Unit();

        Edge e1 = new Edge(n1, n2, false, true, false);
        Edge e2 = new Edge(n2, n3, false, true, false);
        Edge e3 = new Edge(n3, n4, false, true, false);
        Edge e4 = new Edge(n4, n5, false, true, false);

        edgeSet.add(e1);
        edgeSet.add(e2);
        edgeSet.add(e3);
        edgeSet.add(e4);

        // checks lol

        Edge e_check = new Edge(n3, n4, false, true, false);
        if(edgeSet.contains(e_check)){
            System.out.println("identical check good");
        }

        e_check = new Edge(n1, n2, false, false, false);
        if(edgeSet.contains(e_check)){
            System.out.println("diff connect check good");
        }

        e_check = new Edge(n1, n5, false, false, false);
        if(!edgeSet.contains(e_check)) {
            System.out.println("bad edge check good");
        }
    }
}


