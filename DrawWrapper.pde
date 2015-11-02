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
