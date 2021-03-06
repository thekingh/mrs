public class Button {

    private int x;
    private int y;
    private int w;
    private int h;

    private String txt;

    public Button () {
        this(0, 0, 0, 0,"");
    }

    public Button(int x, int y, int w, int h, String txt) {
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        this.txt = txt;
    }

    public Button(float x, float y, float w, float h, String txt) {
        this.w = (int)w;
        this.h = (int)h;
        this.x = (int)x;
        this.y = (int)y;
        this.txt = txt;
    }

    public boolean inBounds(int mx, int my) {
        return ((mx >= x) && (mx <= x + w) && (my >= y) && (my <= y + h));
    }

    public void render(boolean cur_mode) {
        // draw body of button
        pushStyle();
            stroke(0, 0, 0);
            if (cur_mode) {
                fill(255, 255, 255);
            } else {
                fill(150, 150, 150);
            }
            rect(x, y, w, h);
        popStyle();

        // draw button text
        pushStyle();
            textAlign(CENTER, CENTER);
            stroke(0, 0, 0);
            fill(0, 0, 0);
            text(txt, x + w/2, y + h/2);
        popStyle();
    }
} 
