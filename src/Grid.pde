public class Grid {

    private GridObject[][] grid;
    private int h, w;

    /**
        Empty grid constructor
    */
    public Grid() {
        this(0, 0);
    }

    /**
        Grid constructor if given a height and width
        @param h height attribute
        @param w width  attribute
    */
    public Grid(int _h, int _w) {
        h = _h;
        w = _w;

        grid = new GridObject[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = GridObject.EMPTY;
            }
        }
    }

    /**
        Grid constructor if given a grid (copy constructor?)
        @param g Grid to be copied
    */
    public Grid(Grid g) {
       h = g.getHeight(); 
       w = g.getWidth(); 

        grid = new GridObject[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = g.at(i, j);
            }
        }
    }

    /**
        @return width of grid
    */
    public int getWidth() {
        return w;

   }

    /**
        @return height of grid
    */
    public int getHeight() {
        return h;
    }

    /**
        Get GridObject at a given location
        @param r row value 
        @param c column value 
        @return GridObject at cell (r, c)
    */
    public GridObject at(int r, int c) {
        return grid[r][c];
    }

    /**
        Set a given cell at coordinates (r, c) to newObject
        @param r         row value
        @param c         column value
        @param newObject new state to set cell at (r, c) to
        @return          return the previous cell's contents
    */
    public GridObject setState(int r, int c, GridObject newObject) {
       GridObject prev = at(r, c);
       grid[r][c] = newObject;
       return prev;
    }
    
    /**
        Render the grid lines and contents of each grid cell
    */
    public void render() {

        /* find cell sizes */
        int subW = drawer.width / w;
        int subH = drawer.height / h;

        /* draw cell lines */
        drawer.Fill(0, 0, 0);
        for(int i = 1; i < w; i++) {
            /* vertical lines */
            drawer.Line(i * subW, 0, i * subW, drawer.height); 
        }

        for(int i = 1; i < h; i++) {
            /* horizontal lines */
            drawer.Line(0, i * subH, drawer.width, i * subH); 
        }
        
        /* draw contents of each cell */
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if ( at(i, j) == GridObject.VARM) {
                    drawer.Fill(255, 0, 0);
                    drawer.Line((int)((i + 0.5) * subW), j * subH, (int)((i + 0.5) * subW), (j + 1) * subH);
                    drawer.Fill(255, 255, 255);
                } else if (at(i, j) == GridObject.HARM) {
                    drawer.Fill(255, 0, 0);
                    drawer.Line(i * subW, (int)((j + 0.5)*subH), (i + 1) * subW, (int)((j + 0.5) * subH));
                    drawer.Fill(255, 255, 255);
                } else if ( at(i, j) == GridObject.UNIT) {
                    drawer.Fill(0, 255, 0);
                    drawer.Rect(i * subW, j * subH, subW, subH);
                    drawer.Fill(255, 255, 255);
                }
            }
        }
    }

}
