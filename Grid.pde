final int EMPTY =  0;
final int ARM   = -1;
final int UNIT  =  1;

public class Grid {
    private int[][] grid;
    private int h, w;

    public Grid() {
        this(0, 0);
    }

    public Grid(int _h, int _w) {
        h = _h;
        w = _w;

        grid = new int[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    public Grid(Grid g) {
       h = g.getHeight(); 
       w = g.getWidth(); 

        grid = new int[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = g.at(i, j);
            }
        }
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int at(int r, int c) {
        return grid[r][c];
    }

    public int setState(int r, int c, int newState) {
       int prev = at(r, c);
       grid[r][c] = newState;
       return prev;
    }

    public void render() {

        //TODO bake fill, stroke, height/width into drawer
        fill(0, 0, 0);
        for(int i = 1; i < w; i++) {
            drawer.Line(i * (width/w), 0, i * (width/w), height); 
        }

        for(int i = 1; i < h; i++) {
            drawer.Line(0, i * (height/h), width, i * (height/h)); 
        }
        
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if ( at(i, j) == ARM) {
                    fill(255, 0, 0);
                    drawer.Rect(i * (width / w), j * (height / h), (width /w), (height /h));
                    fill(255, 255, 255);
                } else if ( at(i, j) == UNIT) {
                    fill(0, 255, 0);
                    drawer.Rect(i * (width / w), j * (height / h), (width /w), (height /h));
                    fill(255, 255, 255);
                }
            }
        }
    }

}
