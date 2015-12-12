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

public class viz extends PApplet {

String data_path = "states/state";

ArrayList<ArrayList<DrawUnit>> states;
/*ArrayList<DrawUnit> units;*/
int stateCount = 0;
int num_w = 10;
int num_h = 10;
int curState = 0;

public void setup() {
    states = new ArrayList<ArrayList<DrawUnit>>();
    readData();

    size(700, 700);
}

public void readData() {

    String path = "states/state" + stateCount + ".rbt";
    File f = new File(path);

    while(f.exists()) {
    
        String[] lines = loadStrings(path); 
        ArrayList<DrawUnit> units = new ArrayList<DrawUnit>();

        // TODO get rid of \n at end or don't read it at all
        for(int i = 1; i < lines.length; i++) {
            String[] robotString = split(lines[i], ",");

            int[] robotInt    = new int[robotString.length];
            for(int j = 0; j < robotString.length; j++) {
                robotInt[j] = parseInt(robotString[j]);
            }

            DrawUnit u = new DrawUnit(robotInt[0], robotInt[1], robotInt[2], 
                                      robotInt[3], robotInt[4], robotInt[5], 
                                      robotInt[6], robotInt[7], robotInt[8], 
                                      robotInt[9]);
            units.add(u);
        }

        states.add(units);
        stateCount++;

        path = "states/state" + stateCount + ".rbt";
        f = new File(path);
    }
}

public void drawGrid() {
    stroke(0, 20);

    // grid lines
    for(int i = 0; i < num_w; i++) {
        line((width / num_w) * i, 0, (width / num_w) * i, height);
    }

    for(int i = 0; i < num_h; i++) {
        line(0, (height / num_h) * i, width, (height / num_h) * i);
    }

    //axes
    stroke(0, 50);
    line(width / 2, 0, width / 2, height);
    line(0, height/2, width, height/2);

    stroke(0, 100);

}

public void drawRobots(int index) {
    ArrayList<DrawUnit> units = states.get(index);
    for(DrawUnit u: units) {
        u.render();
    }
}

public void keyPressed() {

    if(key == 'h' && curState > 0) {
        curState--;
    }

    if(key == 'l' && curState < stateCount-1) {
        curState++;
    }
}

public void drawFrameNumber() {
    String s = "[" + curState + "/" + stateCount + "]";
    textAlign(RIGHT);
    fill(0);
    text(s, 0, .95f * height, width, height);
}

public void draw() {
    background(200, 200, 200);
    drawGrid();
    drawRobots(curState);
    drawFrameNumber();
}
public class DrawUnit {
    
    int x,y;
    int[]  connections;
    int[]  extensions;

    public DrawUnit() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public DrawUnit(String s) {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

/*    public DrawUnit(int x, int y, int c0, int e0, int c1, int e1, int c2, int e2, int c3, int e3) {*/
    public DrawUnit(int x, int y, int e0, int c0, int e1, int c1, int e2, int c2, int e3, int c3) {
        this.x = x;
        this.y = y;

        connections = new int[4];
        extensions  = new int[4];

        connections[0] = c0;
        connections[1] = c1;
        connections[2] = c2;
        connections[3] = c3;

        extensions[0]  = e0;
        extensions[1]  = e1;
        extensions[2]  = e2;
        extensions[3]  = e3;
    }

    public void drawArm(int dir, int ext, int con) {

        double ratio = 4.0f/5;

        int block_size =  width / num_w;
        int unit_width     = (int)((double)block_size * ratio);

        int margin         = (int)(((1 -  ratio)/2) * (double)block_size);
        int arm_len        = (extensions[dir] == 1) ? (margin + block_size/2) : (margin);
        int disconnect     = (connections[dir] == 1) ? 0 : margin/2;

        int left   = ((block_size * (num_w/2 + x)) + (block_size - unit_width)/2);
        int top    = ((block_size * (num_h/2 - y)) + (block_size - unit_width)/2);
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);

        stroke(0, 255);
        strokeWeight(1);
    
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
    }

    public void drawUnit() {

        int block_size =  width / num_w;
        double ratio = 4.0f/5;
        int unit_width     = (int)((double)block_size * ratio);

        int left   = ((block_size * (num_w/2 + x)) + (block_size - unit_width)/2);
        int top    = ((block_size * (num_h/2 - y)) + (block_size - unit_width)/2);
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);
        
        fill(51, 204, 255);
        stroke(1);
        rect(left, top, unit_width, unit_width);
        fill(255, 255, 255);
    }

    public void render() {

        drawUnit();

        for(int dir = 0; dir < 4; dir++) {
            drawArm(dir, extensions[dir], connections[dir]);
        }

    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "viz" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
