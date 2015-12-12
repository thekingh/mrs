/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 *
 */

package src;

import java.util.Set;
import java.util.List;
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

	private final Set<Node> nodes;
	private Set<Edge> edges;

	public Graph() {
		//Graph(new HashMap<Integer, Node>(), new HashMap<Integer, Edge>());
		this.nodes = new HashSet<Node>();
		this.edges = new HashSet<Edge>();
	}

	public Graph(Set<Node> nodes, Set<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    // public void addEdge(Edge e) {
    //     edges.add(e);
    // }

    public void addNeighbor(Node n, Node neighbor, int dir, boolean isExtended, 
        boolean isConnected) {

        Edge e = n.addNeighbor(neighbor, dir, isExtended, isConnected);
        edges.add(e);
    }

    public void addEdge(Node n1, Node n2, int dir) {
        addEdge(n1, n2, dir, false, true);
    }

    public void addEdge(Node n1, Node n2, int dir, boolean isExtended) {
        addEdge(n1, n2, dir, isExtended, true);
    }

    // dir from 1 to 2
    // NEEDSWORK: default: maybe always contracted and connected
    public void addEdge(Node n1, Node n2, int dir, 
                        boolean isExtended, boolean isConnected) {

        boolean isVertical = ((dir % 2) == 0);

        Edge e = new Edge(n1, n2, isExtended, isConnected, isVertical);

        n1.putEdge(dir, e);
        n2.putEdge((dir + 2) % 4, e);

        edges.add(e);
    }

    public void removeEdge(Edge e) {
        // NEEDSWORK: do we want to leave this in the edge class? make remove here
        e.removeFromNodes();
        edges.remove(e);
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
        return curr.calcRelativeLoc(dir, ext);
    }

    /**
     * Performs update in toGrid operation 
     *
     * @param Q         List of Nodes with location calculated
     * @param E         List of Edges with location calculated
     * @param V         Set of Nodes that have alread been processed
     * @param head      Index of the queue Q's head
     */
    private void addNeighborsToQueue(List<GridObject> Q, List<GridObject> E, Set<Node> V, int head, boolean useExtension) {
        GridObject curr = Q.get(head);
        Node currNode = (Node) curr.o();
        Coordinate currCoord = curr.c();
        for(int dir = 0; dir < 4; dir++) {// TODO revisit
            Node n = currNode.getNeighbor(dir); // n is neighbor TODO WRONG
            // No neighbor in given direction
            if (n == null) {
                continue;
            }

            Edge e = currNode.getEdge(dir);
            final int ext;

            if (!e.isConnected()) {
                continue;
            }

            // Determine if there is an extended arm in given direction
            // and add to edge list
            if (!useExtension && e.isExtended()) { 
                Coordinate ec = calcRelativeLocation(currCoord, dir, 0); 
                GridObject eg = new GridObject(e, ec);
                E.add(eg);
                ext = 1; 
            } else {
                ext = 0;
            }

            // Add node to list if not already visited
            if (!V.contains(n)) {
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

	public Grid toGrid() {
        return toGrid(false);
    }

    /** 
     * Transform a graph into a Grid (Cartesian space)
     *
     * @return          The Cartesian respresentation of a Graph
     */
	public Grid toGrid(boolean useExtension) {
        if (nodes.isEmpty()) { //return default/empty grid
            return new Grid();
        }
        List<GridObject> Q;
        List<GridObject> E;
        Q = new ArrayList<GridObject>();
        E = new ArrayList<GridObject>();
        Set<Node> V = new HashSet<Node>();    //Visited node set
        
        int i = 0;                              //holds list index
        // get random node in the map of id to node
        Node start = nodes.iterator().next();
        GridObject g = new GridObject(start, new Coordinate(0,0));
        Q.add(g);                 //already checked existance
        V.add(start);
        while (i < Q.size()) {
            //GridObject curr = Q.get(i);
            addNeighborsToQueue(Q, E, V, i, useExtension);
            i++;
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
		return new Grid(normQ, normE, w, h);
	}

    /**
     * Updates a relational graph neighbors taking into account spatial
     * considerations.
     *
     * Note that we remove all old edges
     *
     *  Clear previous EdgeSet
     *  For node N:
     *      for direction d:
     *          findClosestNeighbor (in the grid)
     *
     *          if there is no neighbor, delete N's Edge
     *          
     *
     * @param g         spatial representation used in edge update
     */
    public boolean updateGraph(Grid g) {
        List<GridObject> nodeList = g.getNodes();
        Set<Edge>        edgeSet  = new HashSet<Edge>();
        Node obj;
        Edge e;
        Coordinate coord;
        GridObject neighbor;
        boolean isExtended;
        boolean isConnected;
        boolean isVertical;

        for(GridObject V : nodeList) {
            if (!(V.o() instanceof Node)) {
                System.out.println("THings when wrong");
                return false;
            }
            obj   = (Node) V.o();
            coord = V.c();
            for(int dir = 0; dir < 4; dir++) {
                e = obj.getEdge(dir);
                neighbor = g.findClosestNode(coord, dir);
                if (neighbor == null) {
                    obj.removeEdge(dir);
                    continue;
                }
                int dist = coord.mDist(neighbor.c());
                isVertical  = (dir % 2 == 0);
                isConnected = false;
                isExtended  = (dist == 2);
                e = new Edge(obj, (Node)neighbor.o(), isExtended, isConnected, isVertical);
                e.setIsConnected(this.edges.contains(e));
                edgeSet.add(e);
            }
        }
        return true;
    }
}

