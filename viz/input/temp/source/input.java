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

public class input extends PApplet {

/*
    TODO
        - make snapping only to 2x2 boxes, no invalid even allowed
        - make json export good
        - clean up code omg this is so hacky
        - make json export naming scheme
*/



ArrayList<Module> modules;
int num_w = 20;
int num_h = 20;

public void setup() {
    
    modules = new ArrayList<Module>();
}

public void drawGrid() {

    // transparency

    // grid lines
    // vertical lines
    pushStyle();
    for(int i = 0; i < num_w; i++) {
        if( i % 2 == 0) {
            stroke(0, 35);
        } else {
            stroke(0, 8);
        }
        line((width / num_w) * i, 0, (width / num_w) * i, height);
    }

    // horizontal lines
    for(int i = 0; i < num_h; i++) {
        if( i % 2 == 0) {
            stroke(0, 35);
        } else {
            stroke(0, 8);
        }
        line(0, (height / num_h) * i, width, (height / num_h) * i);
    }

    //axes
    stroke(0, 50);
    line(width / 2, 0, width / 2, height);
    line(0, height/2, width, height/2);

    stroke(0, 100);

}

public boolean isValidPlacement(int x, int y) {


    if( (x % 2 == 1)   || (y % 2 == 1)){  // is on even gridline (snapping)
        return false;
    }

    for(Module m: modules) {

        if((x > m.X() - 2) && (x < m.X() + 2) && // has enough h disp
           (y > m.Y() - 2) && (y < m.Y() + 2)) { // has enough v disp
                return false;
        }
    }

    return true;

}

public void highlightGridSpace() {

    int sqr_len = width/num_w;

    int left   = (mouseX / sqr_len) * sqr_len;
    int right  = left + sqr_len;
    int top    = (mouseY / sqr_len) * sqr_len;
    int bottom = top + sqr_len;

    int x = mouseX / sqr_len;
    int y = 20 - (mouseY / sqr_len) - 1;

    pushStyle();
        fill(255, 255, 255);
        if(isValidPlacement(x, y)) { 
            stroke(0, 255, 0);
        } else {
            stroke(255, 0, 0);
        } 
        strokeWeight(2);
        rect(left, top - sqr_len, sqr_len * 2, sqr_len * 2);
    popStyle();
}

public void mouseClicked() {
    int sqr_len = width/num_w;

    int x = (mouseX / sqr_len);
    int y = 20 - (mouseY / sqr_len) - 1; //TODO lol

    print("clicked on coordinate: ");
    println("(" + x + " ," + y + ")");


    if (isValidPlacement(x, y)) {
        println("new module added");
        Module nm = new Module(x, y);
        modules.add(nm);
    }

}

public void drawModules() {
    for(Module m : modules) {
        m.render();
    }
}

public void produceJSON() {    
    JSONArray jrs = new JSONArray();

    for(int i = 0; i < modules.size(); i++) {
        JSONObject jr = new JSONObject();
        //make json object
        jr.setInt("x", modules.get(i).X());
        jr.setInt("y", modules.get(i).Y());

        jr.setInt("con0", 1);
        jr.setInt("ext0", 0);

        jr.setInt("con1", 1);
        jr.setInt("ext1", 0);

        jr.setInt("con2", 1);
        jr.setInt("ext2", 0);

        jr.setInt("con3", 1);
        jr.setInt("ext3", 0);

        jrs.setJSONObject(i, jr);

    }

    saveJSONArray(jrs, "../../src/input.json");

}

public void keyPressed() {
    int p = (int)'p';
    
    if(key == p) {
        produceJSON();
    }
}

public void draw() {

    background(200, 200, 200);
    drawGrid();
    highlightGridSpace();
    drawModules();

}
public class Module {
    Robot[] robots;
    int x; //NOTE THIS IS THE BOTTOM LEFT
    int y;

    public Module() {
        this(0, 0);
    }
    
    public Module(int x, int y) {
        this.x = x;
        this.y = y;

        robots   = new Robot[4];
        robots[0] = new Robot(this.x    , this.y);
        robots[1] = new Robot(this.x + 1, this.y);
        robots[2] = new Robot(this.x, this.y + 1);
        robots[3] = new Robot(this.x + 1, this.y + 1);
    }

    public void render() {
        for(Robot r: robots) {
            r.render();
        }
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }
 }
public class Robot {
    private int x;
    private int y;

    public Robot() {
        this(0, 0);
    }

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public void drawContractedArms(int dir) {

        // how much of block length the unit width is
        double ratio = 4.0f/5;

        // calculate size variables
        int block_size =  width / num_w;
        int unit_width     = (int)((double)block_size * ratio);

        int margin     = (int)(((1 -  ratio)/2) * (double)block_size);
        int arm_len    = margin;
        int disconnect = 0;

        //TODO hacky, but flip y
        int mod_y = 20 - y - 1;

        // unit boundaries
        int left   = ((block_size * x)) + (block_size - unit_width)/2;
        int top    = ((block_size * mod_y)) + (block_size - unit_width)/2;
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);

        pushStyle();
        stroke(0, 255);
        strokeWeight(1);
    
        // draw arm in given direction (arm + "hand")
        if(dir == 0) {
            line(left + (unit_width/2), top, left + unit_width/2, top - arm_len + disconnect);
            line(left + (unit_width/4), top - arm_len + disconnect, right - unit_width/4, top - arm_len + disconnect);
        } else if(dir == 2) {
            line(left + (unit_width/2), bottom, left + unit_width/2, bottom + arm_len - disconnect);
            line(left + (unit_width/4), bottom + arm_len - disconnect, right - unit_width/4, bottom + arm_len - disconnect);
        } else if (dir == 3 ) {
            line(left, top + (unit_width/2), left - arm_len + disconnect , top + unit_width/2 );
            line(left - arm_len + disconnect, top + (unit_width/4), left - arm_len + disconnect, bottom - (unit_width/4));
        } else {
            line(right, top + (unit_width/2), right + arm_len - disconnect, top + unit_width/2 );
            line(right + arm_len - disconnect, top + (unit_width/4), right + arm_len - disconnect, bottom - (unit_width/4));
        }
        popStyle();
    }

    public void drawUnit() {

        // calculate sizes
        int block_size =  width / num_w;
        double ratio = 4.0f/5;
        int unit_width     = (int)((double)block_size * ratio);

        //TODO hacky, but flip y
        int mod_y = 20 - y - 1;

        // unit boundaries
        int left   = ((block_size * x)) + (block_size - unit_width)/2;
        int top    = ((block_size * mod_y)) + (block_size - unit_width)/2;
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);
        
        pushStyle();
            fill(51, 204, 255); // pretty blue
            stroke(1);
            rect(left, top, unit_width, unit_width);
        popStyle();
    }

    public void render() {

        // draw unit body
        drawUnit();

        // draw unit arms
        for(int dir = 0; dir < 4; dir++) {
/*            drawArm(dir, extensions[dir], connections[dir]);*/
            drawContractedArms(dir);
        }

    }
}
  public void settings() {  size(900, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "input" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
