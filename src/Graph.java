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



/**
 * Graph represents a relational network between modular robots. 
 * A Modular robot graph has state containing a nodelist and an edgelist
 * Nodes must subclass the Node abstract class and edges must do the same for 
 * the edge class.
 *
 *
 * @author Alex Tong
 * @author Kabir Singh
 * @author Casey Gowrie
 * @version 1.0
 *
 */
public class Graph {

	private final Map<Integer, Node> nodes;
	private final Set<Edge> edges;

	public Graph() {
		//Graph(new HashMap<Integer, Node>(), new HashMap<Integer, Edge>());
		this.nodes = new HashMap<Integer, Node>();
		this.edges = new HashSet<Edge>();
	}

	public Graph(Map<Integer, Node> nodes, Set<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }


    /**
     * Calculates a new coordinate relative to an input coordinate
     * Note that coordinate system is x pos is right and y pos is up
     *
     * @param curr      The current coordinate in (x,y) representation
     * @param dir       The relative direction of the new point relative to the
     *                  current point note that dir is in the set {0,1,2,3},
     *                  where 0=North, 1=East, 2=South, 3=West.
     * @param ext       The distance between the new point and the current point
     *                  i.e. ext(ension) of 0 is adjacent
     * @return          The new absolute coordinate
     */
    private Coordinate calcRelativeLocation(Coordinate curr, int dir, int ext) {
        int x = curr.x();
        int y = curr.y();
        switch(dir) {
            case 0: //North
                y += 1 + ext;
                break;
            case 1: //East
                x += 1 + ext;
                break;
            case 2: //South
                y -= 1 + ext;
                break;
            case 3: //West
                x -= 1 + ext;
                break;
            default: //Not optimal
                break;
        }
        return new Coordinate(x, y);
    }

    /**
     * Performs update in toGrid operation 
     *
     * @param Q         List of Nodes with location calculated
     * @param E         List of Edges with location calculated
     * @param V         Set of Nodes that have alread been processed
     * @param i         
     */
    private void addNeighborsToQueue(List<GridObject> Q, List<GridObject> E, Set<Node> V, int i) {
        GridObject curr = Q.get(i);
        Node currNode = (Node) curr.o();
        Coordinate currCoord = curr.c();
        for(int dir = 0; dir < 4; dir++) {// TODO revisit
            System.out.println(dir);
            Node n = currNode.getNeighbor(dir); // n is neighbor TODO WRONG
            System.out.println("neighbor");

            if (n != null) {
                System.out.println(n.getId());
            }

            // No neighbor in given direction
            if (n == null) {
                continue;
            }

            Edge e = currNode.getEdge(dir);
            final int ext;

            // Determine if there is an extended arm in given direction
            // and add to edge list
            if (e.isExtended()) { 
                Coordinate ec = calcRelativeLocation(currCoord, dir, 0); 
                GridObject eg = new GridObject(e, ec);
                E.add(eg);
                ext = 1; 
            } else {
                ext = 0;
            }

            // Add node to list if not already visited
            if (!V.contains(n)) {
                System.out.println("add n");
                Coordinate nc = calcRelativeLocation(currCoord, dir, ext);
                GridObject g = new GridObject(n, nc);
                Q.add(g);
                V.add(n);
            }
        }
    }

    /** 
     *  Given a list of grid objects, return a pair of the bounds in 
     *  the negative and positive directions for x and y.
     *
     * @param Q         List of all nodes
     * 
     * @return          Pair of coordinates: (minx, miny) , (maxx, maxy)
     */

    private Pair<Coordinate, Coordinate> getBounds(List<GridObject> Q) {
        int minx = 0;
        int miny = 0;
        int maxx = 0;
        int maxy = 0;
        for(GridObject g : Q) { //TODO better way? 
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


    /** 
     * Given a list of nodes, perform transformation so that all coordinates
     * in the grid are in the first quadrant. 
     *
     * @param Q         List of all nodes
     * @param min       Min location in x and y directions
     * 
     * @return          Transformed list of gridobjects entirely in the
     *                  first quadrant
     */
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

    /** 
     * Transform a graph into a Grid (Cartesian space)
     *
     * @return          The Cartesian respresentation of a Graph
     */
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
        System.out.println("NODES:");
        System.out.println(nodes);
        // get random node in the map of id to node
        Node start = nodes.values().iterator().next();
        GridObject g = new GridObject(start, new Coordinate(0,0));
        Q.add(g);                 //already checked existance
        V.add(start);
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

    public void updateGraph(Grid g) {

        Object[][] grid = g.getGrid();
        List<GridObject> nodeList = g.getNodes();

        // iterate through all nodes
        for(GridObject gridNode : nodeList) {

            // get grid coordinate
            Coordinate gc = gridNode.c();

            // check in each direction
            for(int i = 0; i < 4; i++) {

                // get two adjacent coordinates
                Coordinate adj   = calcRelativeLocation(gc, i, 0);
                Coordinate nAdj  = calcRelativeLocation(adj, i, 0);

                // get previously stored neighbor 
                Node oldNeighbor = ((Node)gridNode.o()).getNeighbor(i);
                boolean isVertical = (i % 2 == 0) ? true : false;

                // check if is in bounds on grid and if new edge is needed
                if(g.inBounds(adj) && checkNewEdge(g, gc, adj, oldNeighbor, i, false)) {
                    
                    Node n1 = ((Node)((GridObject)grid[gc.x() ][gc.y()]).o());
                    Node n2 = ((Node)((GridObject)grid[adj.x()][adj.y()]).o());

                    Edge e = new Edge(n1, n2,false,false, isVertical);
                    edges.add(e);

                } else if (g.inBounds(nAdj) && checkNewEdge(g, gc, nAdj, oldNeighbor,i, true)) {

                    Node n1 = ((Node)((GridObject)grid[gc.x() ][gc.y()]).o());
                    Node n2 = ((Node)((GridObject)grid[nAdj.x()][nAdj.y()]).o());

                    Edge e = new Edge(n1, n2, false, false, isVertical);
                    edges.add(e);
                }
            }
        }
    }

    // TODO stratification right? do I want to change neighbors here?
    public boolean checkNewEdge(Grid g, Coordinate c1, Coordinate c2, Node old, int dir,
                                boolean isExtended) {

        Object[][] grid = g.getGrid();
        GridObject g2 = (GridObject)grid[c2.x()][c2.y()];

        // Check to see if c2 is a node
        if(g2.o() instanceof Node) {
            
            Node n2 = (Node)g2.o();

            // if the old neighbor is the same, do nothing
            if(n2.equals(old)) {
                //TODO case when neighbor is same, but extension is diff
                return false;
            // neighbor is diff, yes to new edge, set new neighbor
            } else {
                Node n1 = ((Node)((GridObject)grid[c1.x() ][c1.y()]).o());
                n1.addNeighbor(n2, dir, isExtended, false);
                return true; 
            }
        }

        return false;
    }
}

