final int EMPTY =  0;
final int HARM  = -1;
final int VARM  = -2;
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

        int subW = drawer.width / w;
        int subH = drawer.height / h;

        drawer.Fill(0, 0, 0);
        for(int i = 1; i < w; i++) {
            drawer.Line(i * subW, 0, i * subW, drawer.height); 
        }

        for(int i = 1; i < h; i++) {
            drawer.Line(0, i * subH, drawer.width, i * subH); 
        }
        
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if ( at(i, j) == ARM) {
                    drawer.Fill(255, 0, 0);
                    drawer.Rect(i * subW, j * subH, subW, subH);
                    drawer.Fill(255, 255, 255);
                } else if ( at(i, j) == UNIT) {
                    drawer.Fill(0, 255, 0);
                    drawer.Rect(i * subW, j * subH, subW, subH);
                    drawer.Fill(255, 255, 255);
                }
            }
        }
    }

}
