public class DrawWrapper {
    
    PApplet p;

    public DrawWrapper(PApplet _p) {
       p = _p; 
    }

    public DrawWrapper() {
       p = null;
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
}
