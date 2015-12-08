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

//String data_path = "states/state";

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
    
        println("State " + stateCount + " exists");
        String[] lines = loadStrings(path); 
        ArrayList<DrawUnit> units = new ArrayList<DrawUnit>();

        for(int i = 1; i < lines.length; i++) {
            String[] robotString = split(lines[i], ",");
            println("parse line " + i);

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

public void draw() {
    background(200, 200, 200);
    drawGrid();
    drawRobots(curState);
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

    public DrawUnit(int x, int y, int c0, int e0, int c1, int e1, int c2, int e2, int c3, int e3) {
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

    public void render() {

        // do some maths, center on axes
        int block_size =  width / num_w;
        int unit_width = block_size/2;

        int extended_len   = unit_width/2;
        int contracted_len = unit_width/8;

        int left   =  (block_size * (num_w/2 + x)) + unit_width/2;
        int top    =  (block_size * (num_h/2 - y)) + unit_width/2;
        int right  =  left + unit_width;
        int bottom =  top  + unit_width;
        


        //draw arms LOL
        //TODO this code is ugly and I hate it 

        //top
        stroke(0, 255);
        strokeWeight(2);
        if(this.extensions[0] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, top - extended_len);
            line(left + (unit_width/4), top - extended_len, right - unit_width/4, top - extended_len);
        } else {
            line(left + (unit_width/2), top, left + unit_width/2, top - contracted_len);
            line(left + (unit_width/4), top - contracted_len, right - unit_width/4, top - contracted_len);
        }

        //bottom
        if(this.extensions[2] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, bottom + extended_len);
            line(left + (unit_width/4), bottom + extended_len, right - unit_width/4, bottom + extended_len);
        } else {
            line(left + (unit_width/2), bottom, left + unit_width/2, bottom + contracted_len);
            line(left + (unit_width/4), bottom + contracted_len, right - unit_width/4, bottom + contracted_len);
        }

        //left
        if(this.extensions[3] == 1) {
            line(left, top + (unit_width/2), left - extended_len , top + unit_width/2 );
            line(left - extended_len, top + (unit_width/4), left - extended_len, bottom - (unit_width/4));
        } else {
            line(left, top + (unit_width/2), left - contracted_len , top + unit_width/2 );
            line(left - contracted_len, top + (unit_width/4), left - contracted_len, bottom - (unit_width/4));
        }

        //right
        if(this.extensions[1] == 1) {
            line(right, top + (unit_width/2), right + extended_len , top + unit_width/2 );
            line(right + extended_len, top + (unit_width/4), right + extended_len, bottom - (unit_width/4));
        } else {
            line(right, top + (unit_width/2), right + contracted_len , top + unit_width/2 );
            line(right + contracted_len, top + (unit_width/4), right + contracted_len, bottom - (unit_width/4));
        }

        strokeWeight(1);

        // draw rectangle, red for right now
        fill(255, 0, 0);
        rect(left, top, (width / num_w)/2, (height / num_h)/2);
        fill(255, 255, 255);

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
