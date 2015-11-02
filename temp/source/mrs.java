import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mrs extends PApplet {

DrawWrapper drawer = new DrawWrapper(this);
Grid g;

public void setup() {
    size(700, 700);
    drawer.setCanvasSize(700, 700);

    g = new Grid(50, 50);
    for(int i = 0; i < g.getWidth(); i++) {
        for(int j = 0; j < g.getHeight(); j++) {
            g.setState(i, j, ((i +j )  % 2));
        }
    }
}

public void draw() {
    background(200, 200, 200);
    g.render();
}
public class DrawWrapper {
    
    PApplet p;
    int height, width;
    

    public DrawWrapper(PApplet _p) {
       p = _p; 
       this.height = 0;
       this.width = 0;
    }

    public DrawWrapper() {
       p = null;
       this.height = 0;
       this.width = 0;
    }

    public void setCanvasSize(int w, int h) {
        this.width = w;
        this.height = h;
    }
    
    public void Line(int x1, int x2, int y1, int y2) {
        p.line(x1, x2, y1, y2);
    }

    public void Rect(int x, int y, int w, int h) {
        p.rect(x, y, w, h);
    }
    
    public void RectMode(int mode) {
        p.rectMode(mode);
    }

    public void Fill(int r, int g, int b) {
        fill(r, g, b);
    }

    public void Stroke(int r, int g, int b) {
        stroke(r, g, b);
    }


}
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

        int subW = drawer.width / w;
        int subH = drawer.height / h;

        //TODO bake fill, stroke, height/width into drawer
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "mrs" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
