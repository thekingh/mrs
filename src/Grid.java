/*
 * Casey Gowrie, Kabir Singh, Alex Tong
 * Senior Capstone Project
 * 
 * Grid.java
 *
 * grid representation of modular robot
 */

package src;

public class Grid {

    private GridObject[][] grid;
    private int h, w;


    public Grid() {
        this(0, 0);
    }

    public Grid(int h, int w) {
        this.h = h;
        this.w = w;

        grid = new GridObject[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = GridObject.EMPTY;
            }
        }

    }

    public Grid(Grid g) {
        this.h = g.getHeight();
        this.w = g.getWidth();

        grid = new GridObject[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = g.at(i, j);
            }
        }

    }

    public int getWidth() {
        return h;
    }

    public int getHeight() {
        return w;
    }


    public GridObject at(int r, int c) {
        return grid[r][c];
    }

    public GridObject set(int r, int c) {
       GridObject prev = at(r, c);
       grid[r][c] = newObject;
       return prev;
    }

    public Graph toGraph() {
        Graph g = new Graph();

        /* iterate through all spaces
           make a new module if needed
           check surroundings to get neighbors/edges
           add to graph
        */

        HashSet<Integer> marked = new HashSet<Integer>();
        
        for(int i = 0; i < w; i++) {
            for(int j = 0; i < h; j++) {

                if(at(i, j) == GridObject.NODE) {
                    if(marked
                    Node n = new Node();
                }

            }
        }


    }

}




