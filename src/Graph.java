/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 */

package src;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Graph {

	private Map<Integer, Node> nodes;
	private Map<Integer, Edge> edges;

	public Graph() {
		//Graph(new HashMap<Integer, Node>(), new HashMap<Integer, Edge>());
		this.nodes = new HashMap<Integer, Node>();
		this.edges = new HashMap<Integer, Edge>();
	}

	public Graph(Map<Integer, Node> nodes, Map<Integer, Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

    private Coordinate calcRelativeLocation(Coordinate curr, int dir, int ext) {
        int x = curr.x();
        int y = curr.y();
        Coordinate nc;
        switch(dir) {
            case 0: //North
                nc = new Coordinate(x, y - (1 + ext));
                break;
            case 1: //East
                nc = new Coordinate(x + (1 + ext), y);
                break;
            case 2: //South
                nc = new Coordinate(x, y + (1 + ext));
                break;
            case 3: //West
                nc = new Coordinate(x - (1 + ext), y);
                break;
            default: // REALLY BAD
                nc = null;
                break;
        }
        return nc;
    }

    private void addNeighborsToQueue(List<GridObject> Q, List<GridObject> E, Set<Node> V, int i) {
        GridObject curr = Q.get(i);
        Node currNode = (Node) curr.o();
        Coordinate currCoord = curr.c();
        for(int dir = 0; dir < 4; dir++) {// little messy might need work
            System.out.println(dir);
            Node n = currNode.getNeighbor(dir); // n is neighbor TODO WRONG
            System.out.println("neighbor");
            if (n != null) {
                System.out.println(n.getId());
            }

            if (n == null) {
                continue;
            }
            Edge e = currNode.getEdge(dir);
            final int ext;
            if (e.isExtended()) { //Add edge to grid also
                Coordinate ec = calcRelativeLocation(currCoord, dir, 0); 
                GridObject eg = new GridObject(e, ec);
                E.add(eg);
                ext = 1; 
            } else {
                ext = 0;
            }
            if (!V.contains(n)) {
                System.out.println("add n");
                Coordinate nc = calcRelativeLocation(currCoord, dir, ext);
                GridObject g = new GridObject(n, nc);
                Q.add(g);
                V.add(n);
            }
        }
    }

    private Pair<Coordinate, Coordinate> getBounds(List<GridObject> Q) {
        int minx = 0;
        int miny = 0;
        int maxx = 0;
        int maxy = 0;
        for(GridObject g : Q) { //TODO fix
            if(g.c().x() > maxx) {
                maxx = g.c().x();
            }
            if(g.c().x() < minx) {
                minx = g.c().x();
            }
            if(g.c().y() > maxy) {
                maxy = g.c().y();
            }
            if(g.c().y() < miny) {
                miny = g.c().y();
            }
        }
        Coordinate min = new Coordinate(minx, miny);
        Coordinate max = new Coordinate(maxx, maxy);
        return new Pair<Coordinate, Coordinate>(min, max);
    }

    private List<GridObject> normalize(List<GridObject> Q, Coordinate min) {
        int x = min.x();
        int y = min.y();
        int len = Q.size();
        List<GridObject> toReturn = new ArrayList<GridObject>();
        for (int i = 0; i < len; i++) {
            GridObject curr = Q.get(i);
            Coordinate new_coord = new Coordinate(curr.c().x() - x, curr.c().y() - y);
            toReturn.add(new GridObject(curr.o(), new_coord));
        }
        return toReturn;
    }

	// TODO
	public Grid toGrid() {
        if (nodes.isEmpty()) { //return default/empty grid
            return new Grid();
        }
        List<GridObject> Q;
        List<GridObject> E;
        Q = new ArrayList<GridObject>();
        E = new ArrayList<GridObject>();
        Set<Node> V = new HashSet<Node>();    //Visited node set
        
        int i = 0;                              //holds list index
        GridObject g = new GridObject(nodes.get(0), new Coordinate(0,0));
        Q.add(g);                 //already checked existance
        V.add(nodes.get(0));
        while (i < Q.size()) {
            //GridObject curr = Q.get(i);
            addNeighborsToQueue(Q, E, V, i);
            i++;
            System.out.println("Printing Q");
            System.out.println(Q);
            System.out.println("Printing V");
            System.out.println(V);
        }
        List<GridObject> all = new ArrayList<GridObject>();
        all.addAll(Q);
        all.addAll(E);
        Pair<Coordinate, Coordinate> bounds = getBounds(all);
        Coordinate min = bounds.a;
        Coordinate max = bounds.b;
        int w   = max.x() - min.x() + 1;
        int h   = max.y() - min.y() + 1;
        List<GridObject> normQ = normalize(Q, min);
        List<GridObject> normE = normalize(E, min);
        System.out.println(w);
        System.out.println(h);
		return new Grid(normQ, normE, w, h);
	}
}




