import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import rutils.*; 
import ralgorithm.*; 
import rgraph.*; 
import java.io.File; 

import ralgorithm.*; 
import rutils.*; 
import rgraph.*; 
import org.json.simple.*; 
import org.json.simple.parser.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class visualizer extends PApplet {

/************************************************************************************************
 *  Authors:    Alex Tong, Casey Gowrie, Kabir Singh
 *  Date:       11 March 2016
 *  Rev-Date:   17 April 2016
 *
 *  Descrption: Ever heard of Dr Frankenstein? Yeah, this is my best impression of him. 
 *              Wouldn't be surprised if this (horrible) code gains sentience and tries to
 *              murder me.
 *
 ***********************************************************************************************/

//TODO scaling error ???
// compute step duration
// connectedness
// clear state






int NUM_W = 20;
int NUM_H = 20;
int num_w = 20;
int num_h = 20;
int DEFAULT_WINDOW_W = 800;
int DEFAULT_WINDOW_H = 1000;
int DEFAULT_GRID_W = 800;
int DEFAULT_GRID_H = 800;

String MODE = "INPUT_START";
//     MODE = "INPUT_END";
//     MODE = "OUPTUT";
String OUTPUT_TYPE = "SLIDING";
//     OUTPUT_TYPE = "COMBING";
//     OUTPUT_TYPE = "TUNNEL";
//     OUTPUT_TYPE = "ELEVATOR";


////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////    DATA PATHS    ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////


String input_start_path = "../../data/combing/input/start.json";
String input_end_path   = "../../data/combing/input/end.json";
String output_path         = "../../data/combing/output/";

String combing_prefix  = "../../data/combing/output/state";
String sliding_prefix  = "../../data/sliding/state";
String tunnel_prefix   = "../../data/tunnel/state";
String elevator_prefix = "../../data/elevator/state";
String expanded_prefix = "../../data/expanded/state";


////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////  INPUT VARIABLES ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

Button mode_button;

Button start_button;
Button end_button;
Button clear_button;
Button save_button;

Button start_connected_button;
Button end_connected_button;
Button size_comparison;

boolean valid_save      = true;
boolean start_connected = false;
boolean end_connected   = false;
ArrayList<InputModule> start_modules;
ArrayList<InputModule> end_modules;

////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// OUTPUT VARIABLES ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//demos
Button combing_button;
Button sliding_button;
Button tunnel_button;
Button elevator_button;

//playback 
Button play_button;
Button pause_button;
Button stepf_button;
Button stepb_button;
Button restart_button;
Button skip_button;

ArrayList<ArrayList<OutputUnit>> states;
int state_count = 0;
int cur_state = 0;
boolean is_playing = false;

////////////////////////////////////////////////////////////////////////////////////////////////////
public void setup() {
    
/*    size(DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);*/

    // make but don't draw input buttons
    init_buttons(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);
/*    readOutputStates(combing_prefix);*/
    
    scaleCanvasToInput();

    start_modules = new ArrayList<InputModule>();
    end_modules   = new ArrayList<InputModule>();
}

public void init_buttons(int cx, int cy, int cw, int ch) {
    // button = new Button (x, y, w, h, "text");

    mode_button = new Button(cx + cw * .85f, cy + ch * 0.05f, cw * 0.1f, ch * 0.1f, "RUN");

    // INPUT BUTTONS
    start_button = new Button(cx + cw * 0.125f, cy + ch * 0.7f, cw * 0.15f, ch * 0.25f, "START");
    start_connected_button = new Button(cx + cw * 0.125f, cy + ch * 0.4f, cw * 0.15f, ch * 0.25f, "SCONNECT");

    end_button   = new Button(cx + cw * 0.325f, cy + ch * 0.7f, cw * 0.15f, ch * 0.25f, "END");
    end_connected_button = new Button(cx + cw * 0.325f, cy + ch * 0.4f, cw * 0.15f, ch * 0.25f, "ECONNECT");

    clear_button = new Button(cx + cw * 0.525f, cy + ch * 0.7f, cw * 0.15f, ch * 0.25f, "CLEAR");
    save_button  = new Button(cx + cw * 0.725f, cy + ch * 0.7f, cw * 0.15f, ch * 0.25f, "SAVE");

    //OUTPUT BUTTONS
    combing_button = new Button(cx + cw * 0.050f, cy + ch * 0.05f, cw * 0.1f, ch * 0.1f, "COMBING");
    sliding_button = new Button(cx + cw * 0.175f, cy + ch * 0.05f, cw * 0.1f, ch * 0.1f, "SLIDING");
    tunnel_button  = new Button(cx + cw * 0.300f, cy + ch * 0.05f, cw * 0.1f, ch * 0.1f, "TUNNEL");
    elevator_button= new Button(cx + cw * 0.425f, cy + ch * 0.05f, cw * 0.1f, ch * 0.1f, "ELEVATOR");

    restart_button = new Button(cx + cw * 0.025f, cy + ch * 0.5f, cw * 0.15f, ch * 0.2f, "RESTART");
    stepb_button   = new Button(cx + cw * 0.225f, cy + ch * 0.5f , cw * 0.15f, ch * 0.2f, "<<");
    play_button    = new Button(cx + cw * 0.425f, cy + ch * 0.35f, cw * 0.15f, ch * 0.2f, "->");
    pause_button   = new Button(cx + cw * 0.425f, cy + ch * 0.65f, cw * 0.15f, ch * 0.2f, "||");
    stepf_button   = new Button(cx + cw * 0.625f, cy + ch * 0.5f , cw * 0.15f, ch * 0.2f, ">>");
    skip_button    = new Button(cx + cw * 0.825f, cy + ch * 0.5f , cw * 0.15f, ch * 0.2f, "SKIP (10)");
    
}

public void scaleCanvasToInput() {
    
    NUM_W = 20;
    NUM_H = 20;
    num_w = 20;
    num_h = 20;
}

public void scaleCanvasToOutput() {

    int global_max_dim = 0;

    for(ArrayList<OutputUnit> cur_robot: states) {
 
        int right_most_x = 0;
        int top_most_y = 0;
        for(int i = 0; i < cur_robot.size(); i++) {
            if(cur_robot.get(i).X() > right_most_x) {
                right_most_x = cur_robot.get(i).X();
            }

            if(cur_robot.get(i).Y() > top_most_y) {
                top_most_y = cur_robot.get(i).Y();
            }
        }

        int local_max_dim = (right_most_x > top_most_y) ? right_most_x : top_most_y;

        if (local_max_dim > global_max_dim) {
            // padding, make sure even
            if(local_max_dim % 2 == 0) {
                local_max_dim += 2;
            } else {
                local_max_dim += 3;
            }

            global_max_dim = local_max_dim;

        }
    }


    NUM_W = global_max_dim;
    NUM_H = global_max_dim;
    num_w = global_max_dim;
    num_h = global_max_dim;



/*    println("rightmost: " + right_most_x);*/

}


public void draw() {
    background (200, 200, 200);

    drawGrid(0, 0, DEFAULT_GRID_W, DEFAULT_GRID_H);

    drawMenu(0, DEFAULT_GRID_H,  DEFAULT_WINDOW_W - 1, 
                DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);

    if(MODE == "OUTPUT") {
        drawOutputRobot(cur_state);
        drawFrameNumber();

        // play loop
        if(is_playing && cur_state < state_count - 1) {
            cur_state++;
            delay(400);

            if(cur_state == state_count) {
                is_playing = false;
            }
        }


    } else {
        highlightGridSpace();
        drawInputModules();
    }

    valid_save = isValidSave();
}

public void mouseClicked() {

    /////////////////////
    // MODULE CLICKING //
    /////////////////////

    if (mouseY < DEFAULT_GRID_H) {
        int sqr_len = width/num_w;

        int x = (mouseX / sqr_len);
        int y = 20 - (mouseY / sqr_len) - 1; 

        if (isValidPlacement(x, y)) {
            InputModule nm = new InputModule(x, y);
            if(MODE == "INPUT_START") {
                start_modules.add(nm);
                start_connected = updateConnectivity(start_modules);
            } else if (MODE == "INPUT_END") {
                end_modules.add(nm);
                end_connected = updateConnectivity(end_modules);
            }
        }
    }

    /////////////////////
    // BUTTON CLICKING //
    /////////////////////

    // INPUT //
    if (start_button.inBounds(mouseX, mouseY)) {
        MODE = "INPUT_START";
    }

    if (end_button.inBounds(mouseX, mouseY)) {
        MODE = "INPUT_END";
    }

    if (clear_button.inBounds(mouseX, mouseY)) {
        start_modules.clear();
        end_modules.clear();
        valid_save = false;
        start_connected = false;
        end_connected = false;
    }

    if (valid_save && save_button.inBounds(mouseX, mouseY)) {
        exportToJSON(start_modules, input_start_path, true);
        exportToJSON(end_modules, input_end_path, false);
    }

    // OUTPUT //
    if (!is_playing && play_button.inBounds(mouseX, mouseY)) {
        is_playing = true;
    }

    if (is_playing && pause_button.inBounds(mouseX, mouseY)) {
        is_playing = false;
    }
    
    
    if (!is_playing && cur_state < state_count - 1 && stepf_button.inBounds(mouseX, mouseY)) {
        cur_state++;
    }

    if (!is_playing && cur_state > 0 && stepb_button.inBounds(mouseX, mouseY)) {
        cur_state--;
    }

    if (cur_state > 0 && restart_button.inBounds(mouseX, mouseY)) {
        cur_state = 0;
    }

    if (cur_state + 10 < state_count && skip_button.inBounds(mouseX, mouseY)) {
        cur_state += 10;
    }


    // UNIVERSAL //
    if (mode_button.inBounds(mouseX, mouseY)) {
        if(MODE == "OUTPUT") {
            MODE = "INPUT_START";
            scaleCanvasToInput();
            states.clear();
            state_count = 0;
        } else if(MODE == "INPUT_START" || MODE == "INPUT_END") {
            MODE = "OUTPUT";
            RunCombing.JSONCombHelper(output_path, input_start_path, input_end_path);
            delay(500);
            readOutputStates(combing_prefix);
            scaleCanvasToOutput();
            println("done waiting...");
        }
    }
}

// cx, cy is top left corner 
// (0, 0) in proceessing is top left fyi
public void drawGrid(int cx, int cy, int cw, int ch) {
    
    pushStyle(); 
    for(int i = 0; i < NUM_W; i++) {
        if( i % 2 == 0) {
            stroke(0, 35);
        } else {
            stroke(0, 15);
        }

        line((cw / NUM_W) * i, 0, (cw / NUM_W) * i, ch);
    }

    // horizontal lines
    for(int i = 0; i < NUM_H; i++) {
        if( i % 2 == 0) {
            stroke(0, 35);
        } else {
            stroke(0, 15);
        }
        line(0, (ch / NUM_H) * i, cw, (ch / NUM_H) * i);
    }

    stroke(0, 80);
/*    line(cw / 2, 0, cw / 2, ch);*/
/*    line(0, ch/2, cw, ch/2);*/

    stroke(0, 100);

    popStyle();

}

public boolean updateConnectivity(ArrayList<InputModule> robot) {
    InputModule newModule = robot.get(robot.size() - 1); // last added
    boolean stillConnected = true;

    for (int i = 0; i < robot.size(); i++) {
        InputModule tmp = robot.get(i);

        println("checking against");
        println("tmp: " + tmp.X() + ", " + tmp.Y());
        println("new: " + newModule.X() + ", " + newModule.Y());

        // below
        if ((tmp.X() == newModule.X()) && (tmp.Y() == newModule.Y() + 2)) {
            stillConnected = true;
            break;
        // above
        } else if ((tmp.X() == newModule.X()) && (tmp.Y() == newModule.Y() - 2)) {
            stillConnected = true;
            break;
        //left
        } else if ((tmp.X() == newModule.X() + 2) && (tmp.Y() == newModule.Y())) {
            stillConnected = true;
            break;
        //right
        } else if ((tmp.X() == newModule.X() - 2) && (tmp.Y() == newModule.Y())) {
            stillConnected = true;
            break;
        } else {
            println("\tadded unconnected");
            println("\ttmp: " + tmp.X() + ", " + tmp.Y());
            println("\tnew: " + newModule.X() + ", " + newModule.Y());
            stillConnected = false;
        } 
    }

    if (stillConnected) {
        println("*******STILL CONNECTED*********\n");
    } else {
        println("YOU DONE BROKED IT\n");
    }

    return stillConnected;
}

public boolean isValidSave(){
    // check to see size of each arr is same
    // check to see if both bots are connected
    return (start_modules.size() == end_modules.size()) && (start_connected) && (end_connected);
}


public boolean isValidPlacement(int x, int y) {

    // Is on even gridline (snapping)
    if( (x % 2 == 1)   || (y % 2 == 1)){  
        return false;
    }

    if(MODE == "INPUT_START") {
        for(InputModule m: start_modules) {
            // Check to see if a module placed at (x, y) wouldn't overlap with existing modules.
            if((x > m.X() - 2) && (x < m.X() + 2) && 
               (y > m.Y() - 2) && (y < m.Y() + 2)) { 
                    return false;
            }
        }
    } else if (MODE == "INPUT_END") {
        for(InputModule m: end_modules) {
            // Check to see if a module placed at (x, y) wouldn't overlap with existing modules.
            if((x > m.X() - 2) && (x < m.X() + 2) && 
               (y > m.Y() - 2) && (y < m.Y() + 2)) { 
                    return false;
            }
        }
    }
    return true;

}

public void highlightGridSpace() {

    if(mouseY >= DEFAULT_GRID_H) {    
        return; 
    }

    int sqr_len = width/num_w;

    // Get L and R coordinates on canvas of current box being hovered over
    int left   = (mouseX / sqr_len) * sqr_len;
    int right  = left + sqr_len;
    int top    = (mouseY / sqr_len) * sqr_len;

    // Get X and Y coordinates 
    int x = mouseX / sqr_len;
    int y = 20 - (mouseY / sqr_len) - 1;

    pushStyle();
        //white square by default
        fill(255, 255, 255); 
        if(isValidPlacement(x, y)) { 
             // green if valid 
            stroke(0, 255, 0);
        } else {
             // red if invalid 
            stroke(255, 0, 0);
        } 
        strokeWeight(2);
        rect(left, top - sqr_len, sqr_len * 2, sqr_len * 2);
    popStyle();
}

public void drawInputModules() {
    // goddammit where the fuck are my pointers
    if(MODE == "INPUT_START") {
        // order matters
        for(InputModule m : end_modules) {
            m.render(false);
        }

        for(InputModule m : start_modules) {
            m.render(true);
        }

    } else if (MODE == "INPUT_END") {

        for(InputModule m : start_modules) {
            m.render(false);
        }

        for(InputModule m : end_modules) {
            m.render(true);
        }
    }
}

public void drawOutputRobot(int index) {
    ArrayList<OutputUnit> units = states.get(index);

    for(OutputUnit u: units) {
        u.render();
    }
}

public void drawFrameNumber() {
    String s = "[" + (cur_state+1) + "/" + state_count + "]";

    // draw in bottom right of window
    textAlign(CENTER);
    fill(0);
    text(s, 0, .85f * DEFAULT_WINDOW_H, DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);
}

public void drawMenu(int cx, int cy, int cw, int ch) {

    pushStyle();
        stroke(0, 0, 0);
        fill(230, 230, 230);
        rect(cx, cy, cw, ch);
        
        fill(255, 0, 0);
        textAlign(CENTER);
        //text(MODE, DEFAULT_WINDOW_W/2, 900);
        mode_button.render(true);

        // draw appropriate buttons
        if (MODE == "OUTPUT") {

            combing_button.render(OUTPUT_TYPE == "COMBING");
            sliding_button.render(OUTPUT_TYPE == "SLIDING");
            tunnel_button.render(OUTPUT_TYPE == "TUNNEL");
            elevator_button.render(OUTPUT_TYPE == "ELEVATOR");

            stepb_button.render(cur_state > 0);
            play_button.render(!is_playing);
            pause_button.render(is_playing);
            stepf_button.render( cur_state < state_count - 1);
            restart_button.render(cur_state > 0);
            skip_button.render(cur_state + 10 < state_count);

        } else {
            if (MODE == "INPUT_START") {
                start_button.render(true);
                end_button.render(false);
            } else if  (MODE == "INPUT_END") {
                start_button.render(false);
                end_button.render(true);
            }

            if(start_modules.size() > 0 || end_modules.size() > 0) {
                clear_button.render(true);
            } else {
                clear_button.render(false);
            }

            save_button.render(valid_save);
        
            start_connected_button.render(start_connected);
            end_connected_button.render(end_connected);

        }


    popStyle();
}

////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////   HELPER FUNCS   ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

public ArrayList<InputModule> sanitize (ArrayList<InputModule> modules) {
    

    int left = NUM_W;
    int bottom = NUM_H;

    // find bottom left-most coordinate -> generate offset for x and y
    for(InputModule m: modules) {

        if(m.X() < left) {
            left = m.X();
        }

        if(m.Y() < bottom) {
            bottom = m.Y();
        }
    
    }

    // move by offset
    for(InputModule m: modules) {
        m.setX(m.X() - left); 
        m.setY(m.Y() - bottom); 
    }

    return modules;
}

public ArrayList<InputModule> invert (ArrayList<InputModule> modules) {
         
    int x, y;
    for(InputModule m: modules) {
        x = m.X();
        y = m.Y();
        m.setX(y);
        m.setY(x);
    }

    return modules;

}

public ArrayList<InputModule> mirror(ArrayList<InputModule> modules) {

    // find bounding box
    int w = 0;
    int h = 0;
    for(InputModule m: modules) {

        if(m.X() > w) {
            w = m.X();
        }

        if(m.Y() > h) {
            h = m.Y();
        }
    
    }
         
    int x, y;
    for(InputModule m: modules) {
        x = m.X();
        y = m.Y();
        m.setX(w - x);
    }

    return modules;

}


public void clearDirectory(String path) {
    File dir = new File(path);
    for(File f : dir.listFiles()) {
        f.delete();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////    JSON FUNCS    ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

public void exportToJSON(ArrayList<InputModule> modules, String path, boolean clean) {

    // Preprocess modules 
    ArrayList<InputModule> sanitized = modules;
/*    if (clean) {*/
        sanitized = mirror(invert(sanitize(modules)));
/*    }*/

    processing.data.JSONArray jrs = new processing.data.JSONArray();

    for(int i = 0; i < sanitized.size(); i++) {
        processing.data.JSONObject jr = new processing.data.JSONObject();

        //make json object
        jr.setInt("x", sanitized.get(i).X());
        jr.setInt("y", sanitized.get(i).Y());

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


/*    clearDirectory("../../data/combing/output/");*/
    saveJSONArray(jrs, path);

}

public void readOutputStates(String path_prefix) {

    states = new ArrayList<ArrayList<OutputUnit>>();
    String path = path_prefix + state_count + ".json";
    File f = new File(path);
    println("reading output states from: " + path);

    while(f.exists()) {
        
        // load json array of robots
        ArrayList<OutputUnit> units = new ArrayList<OutputUnit>();
        processing.data.JSONArray robotArray = loadJSONArray(path);

        // for each robot, make a draw unit and add to state
        for(int i = 0; i < robotArray.size(); i++) {
            processing.data.JSONObject robot = robotArray.getJSONObject(i);
            
            OutputUnit u = new OutputUnit(robot.getInt("x"),
                                          robot.getInt("y"),
                                          robot.getInt("ext0"),
                                          robot.getInt("con0"),
                                          robot.getInt("ext1"),
                                          robot.getInt("con1"),
                                          robot.getInt("ext2"),
                                          robot.getInt("con2"),
                                          robot.getInt("ext3"),
                                          robot.getInt("con3"));

            units.add(u);
        }

        states.add(units);
        println("Loaded state: " + state_count);
        state_count++;

        // try getting next enumerated state
        path = path_prefix + state_count + ".json";
        f = new File(path);
    }
}
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
/** 
 * Module class exclusively for the input visualiztion
 *
 *  @author Casey Gowrie
 *  @author Alex Tong
 *  @author Kabir Singh
 *  
 *  @version 1.0
 */


public class InputModule {
    private InputUnit[] units;
    private int x; //NOTE THIS IS THE BOTTOM LEFT
    private int y; //OF MODULE CLUSTER

    public InputModule() {
        this(0, 0);
    }
    
    /**
     * Given an x and y coordinate, construct a new module containing
     * four robots (w/ the x and y coordinates the bottom left corner
     * of the module's footprint
     *
     * @param x leftmost bound of module's footprint
     * @param y bottommost bound of module's footprint
     */
    public InputModule(int x, int y) {
        this.x = x;
        this.y = y;

        units    = new InputUnit[4];
        units[0] = new InputUnit(this.x    , this.y);
        units[1] = new InputUnit(this.x + 1, this.y);
        units[2] = new InputUnit(this.x, this.y + 1);
        units[3] = new InputUnit(this.x + 1, this.y + 1);
    } 

    /**
     * Render all individual units of a module
     */
    public void render(boolean cur) {
        for(InputUnit u: units) {
            u.render(cur);
        }
    }

    /**
     *  Return the leftmost bound of the module's footprint
     */
    public int X() {
        return x;
    }

    /**
     *  Return the bottommost bound of the module's footprint
     */
    public int Y() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
 }
/** 
 *  Unit class exclusively for the input visualiztion
 *
 *  @author Casey Gowrie
 *  @author Alex Tong
 *  @author Kabir Singh
 *  
 *  @version 1.0
 */

public class InputUnit {
    private int x;
    private int y;

    public InputUnit() {
        this(0, 0);
    }

    /**
     * Given an x and y coordinate, construct a new unit at that location
     *
     * @param x x coordinate of unit
     * @param y y coordinate of unit
     */
    public InputUnit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    /**
     * Given a direction, draw a robot arm; reused code from the more dynamic
     * output visualization unit class.
     */
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

    /**
     * Draws the unit's body 
     */
    public void drawUnit(boolean cur) {

        //TODO push matrix to translate off a little bit??
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
            stroke(1);
            if (!cur) {
                fill(50, 50, 50); 
                pushMatrix();
                    translate(3, -3);
                    rect(left, top, unit_width, unit_width);
                popMatrix();
            } else {
                fill(51, 204, 255); // pretty blue
                rect(left, top, unit_width, unit_width);
            }
        popStyle();
    }

    /**
     * Render the entire unit
     */
    public void render(boolean cur) {

        // draw unit body
        drawUnit(cur);

        // draw unit arms
        if (cur) {
            for(int dir = 0; dir < 4; dir++) {
    /*            drawArm(dir, extensions[dir], connections[dir]);*/
                drawContractedArms(dir);
            }
        }

    }
}
public class OutputUnit {
    
    private int x,y;
    private int[]  connections;
    private int[]  extensions;

    public OutputUnit() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public OutputUnit(String s) {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public OutputUnit(int x, int y, int e0, int c0, int e1, int c1, int e2, int c2, int e3, int c3) {
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

    public int X() {
        return this.x;
    }

    public int Y() {
        return this.y;
    }

    public void drawArm(int dir, int ext, int con) {

        // how much of block length the unit width is
        double ratio = 4.0f/5;

        // calculate size variables
        int block_size =  width / num_w;
        int unit_width     = (int)((double)block_size * ratio);

        int margin     = (int)(((1 -  ratio)/2) * (double)block_size);
        int arm_len    = (extensions[dir]  == 1) ? (margin + block_size/2) : (margin);
        int disconnect = (connections[dir] == 1) ? (0) : (margin/2);

        // unit boundaries

        int mod_y = NUM_H - y - 1;
        int left   = ((block_size * x)) + (block_size - unit_width)/2;
        int top    = ((block_size * mod_y)) + (block_size - unit_width)/2;
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);

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
    }

    public void drawUnit() {

        // calculate sizes
        int block_size =  width / num_w;
        double ratio = 4.0f/5;
        int unit_width     = (int)((double)block_size * ratio);

        // unit boundaries

        int mod_y = NUM_H - y - 1;
        int left   = ((block_size * x)) + (block_size - unit_width)/2;
        int top    = ((block_size * mod_y)) + (block_size - unit_width)/2;
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);
        
        fill(51, 204, 255); // pretty blue
        stroke(1);
        rect(left, top, unit_width, unit_width);
        fill(255, 255, 255);
    }

    public void render() {

        // draw unit body
        drawUnit();

        // draw unit arms
        for(int dir = 0; dir < 4; dir++) {
            drawArm(dir, extensions[dir], connections[dir]);
        }

    }
}
  public void settings() {  size(800, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "visualizer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
