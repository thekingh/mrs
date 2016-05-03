/************************************************************************************************
 *  Authors:     Alex Tong, Casey Gowrie, Kabir Singh
 *  Date:        11 March 2016
 *  Rev-Date:    01 May   2016
 *
 *  Description: Frankensteined-together visualization tool for the MRS project. Has the ability
 *               to let user input a start and end state robot, perform a cursory validity test,
 *               and pass the start/end states to the backend. The output can then be stepped
 *               through or "played." 
 *               
 *               Also contains demos for some complex movements in output mode.
 *
 ***********************************************************************************************/

import rutils.*;
import ralgorithm.*;
import rgraph.*;
import java.io.File;

////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////      GLOBAL      ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

int NUM_W = 20;
int NUM_H = 20;
int DEFAULT_WINDOW_W = 800;
int DEFAULT_WINDOW_H = 1000;
int DEFAULT_GRID_W = 800;
int DEFAULT_GRID_H = 800;

String MODE = "INPUT_START";
//     MODE = "INPUT_END";
//     MODE = "OUPTUT";
String OUTPUT_TYPE = "COMBING";
//     OUTPUT_TYPE = "STAIRCASE";
//     OUTPUT_TYPE = "TUNNEL";
//     OUTPUT_TYPE = "ELEVATOR";


////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////    DATA PATHS    ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////


String input_start_path = "../data/combing/input/start.json";
String input_end_path   = "../data/combing/input/end.json";
String output_path      = "../data/combing/output/";

String combing_prefix   = "../data/combing/output/state";
String staircase_prefix = "../data/staircase/state";
String elevator_prefix  = "../data/elevator/state";
String expanded_prefix  = "../data/expanded/state";


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
Button staircase_button;
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
void setup() {
    size(800, 1000);

    // create buttons, but not nec. render
    init_buttons(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);
    
    // scale canvas to default input size
    scaleCanvasToInput();

    start_modules = new ArrayList<InputModule>();
    end_modules   = new ArrayList<InputModule>();
}

void init_buttons(int cx, int cy, int cw, int ch) {
    // new button signature:
    // button = new Button (x, y, w, h, "text");

    mode_button = new Button(cx + cw * .85, cy + ch * 0.05, cw * 0.1, ch * 0.1, "RUN");

    // INPUT BUTTONS
    start_button = new Button(cx + cw * 0.125, cy + ch * 0.7, cw * 0.15, ch * 0.25, "START");
    start_connected_button = new Button(cx + cw * 0.125, cy + ch * 0.4, cw * 0.15, ch * 0.25, "SCONNECT");

    end_button   = new Button(cx + cw * 0.325, cy + ch * 0.7, cw * 0.15, ch * 0.25, "END");
    end_connected_button = new Button(cx + cw * 0.325, cy + ch * 0.4, cw * 0.15, ch * 0.25, "ECONNECT");

    clear_button = new Button(cx + cw * 0.525, cy + ch * 0.7, cw * 0.15, ch * 0.25, "CLEAR");
    save_button  = new Button(cx + cw * 0.725, cy + ch * 0.7, cw * 0.15, ch * 0.25, "SAVE");

    //OUTPUT BUTTONS
    combing_button = new Button(cx + cw * 0.050, cy + ch * 0.05, cw * 0.1, ch * 0.1, "COMBING");
    staircase_button = new Button(cx + cw * 0.175, cy + ch * 0.05, cw * 0.1, ch * 0.1, "STAIRCASE");
    elevator_button  = new Button(cx + cw * 0.300, cy + ch * 0.05, cw * 0.1, ch * 0.1, "ELEVATOR");

    restart_button = new Button(cx + cw * 0.025, cy + ch * 0.5, cw * 0.15, ch * 0.2, "RESTART");
    stepb_button   = new Button(cx + cw * 0.225, cy + ch * 0.5 , cw * 0.15, ch * 0.2, "<<");
    play_button    = new Button(cx + cw * 0.425, cy + ch * 0.35, cw * 0.15, ch * 0.2, "->");
    pause_button   = new Button(cx + cw * 0.425, cy + ch * 0.65, cw * 0.15, ch * 0.2, "||");
    stepf_button   = new Button(cx + cw * 0.625, cy + ch * 0.5 , cw * 0.15, ch * 0.2, ">>");
    skip_button    = new Button(cx + cw * 0.825, cy + ch * 0.5 , cw * 0.15, ch * 0.2, "SKIP (10)");
    
}

void scaleCanvasToInput() {
    
    NUM_W = 20;
    NUM_H = 20;
}

// Step through all output states and determine scaling to visualize
// largest robot
void scaleCanvasToOutput() {

    int global_max_dim = 0;

    // Check every state and find state-maximums
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
            // padding, make sure dims are always even
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

}


void draw() {
    // always reset to "blank" canvas
    background (200, 200, 200);

    // draw the grid lines
    drawGrid(0, 0, DEFAULT_GRID_W, DEFAULT_GRID_H);

    // draw the menu box (buttons, etc.)
    drawMenu(0, DEFAULT_GRID_H,  DEFAULT_WINDOW_W - 1, 
                DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);

    // if in output mode, draw output modules 
    if(MODE == "OUTPUT") {
        drawOutputRobot(cur_state);
        drawFrameNumber();

        // play loop
        if(is_playing && cur_state < state_count - 1) {
            cur_state++;
            delay(100);

            if(cur_state == state_count) {
                is_playing = false;
            }
        }

    // if in input mode, draw input modules and input highlighting
    } else {
        highlightGridSpace();
        drawInputModules();
        valid_save = isValidSave();
    }

}

// Build in processing function for handling the mouse click event
void mouseClicked() {

    /////////////////////
    // MODULE CLICKING //
    /////////////////////

    if (mouseY < DEFAULT_GRID_H) {
        int sqr_len = width/NUM_W;

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

    //////////////////////////////
    // BUTTON CLICKING -- INPUT //
    //////////////////////////////

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
        exportToJSON(start_modules, input_start_path);
        exportToJSON(end_modules, input_end_path);
        println("States have been saved!");
    }

    ///////////////////////////////
    // BUTTON CLICKING -- OUTPUT //
    ///////////////////////////////

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

    if (!is_playing && OUTPUT_TYPE != "COMBING" && combing_button.inBounds(mouseX, mouseY)) {
        OUTPUT_TYPE = "COMBING";
        cur_state = 0;
        state_count = 0;
        states.clear();
        readOutputStates(combing_prefix);
        scaleCanvasToOutput();
        println("Loaded combing demo");
    }
    
    if (!is_playing && OUTPUT_TYPE != "STAIRCASE" && staircase_button.inBounds(mouseX, mouseY)) {
        OUTPUT_TYPE = "STAIRCASE";
        cur_state = 0;
        state_count = 0;
        states.clear();
        readOutputStates(staircase_prefix);
        scaleCanvasToOutput();
        println("Loaded staircase demo");
    }

    if (!is_playing && OUTPUT_TYPE != "ELEVATOR" && elevator_button.inBounds(mouseX, mouseY)) {
        OUTPUT_TYPE = "ELEVATOR";
        cur_state = 0;
        state_count = 0;
        states.clear();
        readOutputStates(elevator_prefix);
        scaleCanvasToOutput();
        println("Loaded elevator demo");
    }


    //////////////////////////////////
    // BUTTON CLICKING -- UNIVERSAL //
    //////////////////////////////////
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

// Draw the grid lines, highlighting the spaces where modules occupy 
// (0, 0) in proceessing is top left fyi, so cx, cy is top left corner 
void drawGrid(int cx, int cy, int cw, int ch) {
    
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

    popStyle();

}

// Primitive connectivity check (no islands of single modules)
// TODO make more robust (DFS check)
public boolean updateConnectivity(ArrayList<InputModule> robot) {
    InputModule newModule = robot.get(robot.size() - 1); // last added
    boolean stillConnected = true;

    for (int i = 0; i < robot.size(); i++) {
        InputModule tmp = robot.get(i);

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
            stillConnected = false;
        } 
    }

    if (stillConnected) {
        print("** still \"connected\" **\n");
    } else if (!stillConnected && robot.size() > 1) {
        print("xx no longer \"connected\" xx\n");
    }

    return stillConnected;
}

// Continuously called function to ensure start/end combo is valid
public boolean isValidSave(){
    // check to see size of each arr is same
    // check to see if both bots are "connected"
    return (start_modules.size() == end_modules.size()) && (start_connected) && (end_connected);
}


// Placement checking function -- ensure that no module exists at spot and that spot
// is a module space (inside thicker grid lines)
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

// Highlight the current space the user is hovering over with the mouse
void highlightGridSpace() {

    // if the mouse is too far "down", ignore
    if(mouseY >= DEFAULT_GRID_H) {    
        return; 
    }

    int sqr_len = width/NUM_W;

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

// Draw the user created input modules -- make sure to output in the
// correct order
void drawInputModules() {
    
    // note: this would be easier with pointers >:(

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

// Draw the output robots for a given state number
void drawOutputRobot(int index) {
    ArrayList<OutputUnit> units = states.get(index);

    for(OutputUnit u: units) {
        u.render();
    }
}

// Draw a state number indicator above the play button
void drawFrameNumber() {
    String s = "[" + (cur_state+1) + "/" + state_count + "]";

    // draw in bottom right of window
    textAlign(CENTER);
    fill(0);
    text(s, 0, .85 * DEFAULT_WINDOW_H, DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);
}

// Draw the elements below the grid
void drawMenu(int cx, int cy, int cw, int ch) {

    pushStyle();

        // menu "canvas"
        stroke(0, 0, 0);
        fill(230, 230, 230);
        rect(cx, cy, cw, ch);
        
        fill(255, 0, 0);
        textAlign(CENTER);
        mode_button.render(true);

        // draw appropriate buttons depending on mode
        if (MODE == "OUTPUT") {

            combing_button.render(OUTPUT_TYPE == "COMBING");
            staircase_button.render(OUTPUT_TYPE == "STAIRCASE");
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

// offset all module locations as far left and down as possible
ArrayList<InputModule> offset (ArrayList<InputModule> modules) {
    
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

// switch x and y of all module locations
ArrayList<InputModule> invert (ArrayList<InputModule> modules) {
         
    int old_x, old_y;
    for(InputModule m: modules) {
        old_x = m.X();
        old_y = m.Y();
        m.setX(old_y);
        m.setY(old_x);
    }

    return modules;

}

// mirror module locations across y-axis (?)
ArrayList<InputModule> mirror(ArrayList<InputModule> modules) {

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

////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////    JSON FUNCS    ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

// Export a given module array to a json file, and write to path location
void exportToJSON(ArrayList<InputModule> modules, String path) {

    // Preprocess modules 
    ArrayList<InputModule> sanitized = mirror(invert(offset(modules)));

    processing.data.JSONArray json_arr = new processing.data.JSONArray();

    for(int i = 0; i < sanitized.size(); i++) {
        processing.data.JSONObject json_obj = new processing.data.JSONObject();

        //make json object
        json_obj.setInt("x", sanitized.get(i).X());
        json_obj.setInt("y", sanitized.get(i).Y());

        json_obj.setInt("con0", 1);
        json_obj.setInt("ext0", 0);

        json_obj.setInt("con1", 1);
        json_obj.setInt("ext1", 0);

        json_obj.setInt("con2", 1);
        json_obj.setInt("ext2", 0);

        json_obj.setInt("con3", 1);
        json_obj.setInt("ext3", 0);

        // place in json array
        json_arr.setJSONObject(i, json_obj);

    }

    saveJSONArray(json_arr, path);

}

// Given a path, read all state files present into a ArrayList
// of OuputStates
void readOutputStates(String path_prefix) {

    states = new ArrayList<ArrayList<OutputUnit>>();
    String path = path_prefix + state_count + ".json";
    File f = new File(path);
    println("Reading output states from: " + path);

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
