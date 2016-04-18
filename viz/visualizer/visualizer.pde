//TODO scaling on input/oupt

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


////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////    DEMO PATHS    ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
/* TODO change these to /data/ */
String combing_prefix  = "output_states/combing/state";
String sliding_prefix  = "output_states/sliding/state";
String tunnel_prefix   = "output_states/tunnel/state";
String elevator_prefix = "output_states/elevator/state";

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
/*    size(DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);*/

    // make but don't draw input buttons
    init_buttons(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);
    readOutputStates(sliding_prefix);

    start_modules = new ArrayList<InputModule>();
    end_modules   = new ArrayList<InputModule>();
}

void init_buttons(int cx, int cy, int cw, int ch) {
    // button = new Button (x, y, w, h, "text");

    mode_button = new Button(cx + cw * .85, cy + ch * 0.05, cw * 0.1, ch * 0.1, "<->");

    // INPUT BUTTONS
    start_button = new Button(cx + cw * 0.125, cy + ch * 0.7, cw * 0.15, ch * 0.25, "START");
    start_connected_button = new Button(cx + cw * 0.125, cy + ch * 0.4, cw * 0.15, ch * 0.25, "SCONNECT");

    end_button   = new Button(cx + cw * 0.325, cy + ch * 0.7, cw * 0.15, ch * 0.25, "END");
    end_connected_button = new Button(cx + cw * 0.325, cy + ch * 0.4, cw * 0.15, ch * 0.25, "ECONNECT");

    clear_button = new Button(cx + cw * 0.525, cy + ch * 0.7, cw * 0.15, ch * 0.25, "CLEAR");
    save_button  = new Button(cx + cw * 0.725, cy + ch * 0.7, cw * 0.15, ch * 0.25, "SAVE");

    //OUTPUT BUTTONS
    restart_button = new Button (cx + cw * 0.025, cy + ch * 0.5, cw * 0.15, ch * 0.2, "RESTART");
    stepb_button   = new Button(cx + cw * 0.225, cy + ch * 0.5 , cw * 0.15, ch * 0.2, "<<");
    play_button    = new Button(cx + cw * 0.425, cy + ch * 0.35, cw * 0.15, ch * 0.2, "->");
    pause_button   = new Button(cx + cw * 0.425, cy + ch * 0.65, cw * 0.15, ch * 0.2, "||");
    stepf_button   = new Button(cx + cw * 0.625, cy + ch * 0.5 , cw * 0.15, ch * 0.2, ">>");
    skip_button    = new Button(cx + cw * 0.825, cy + ch * 0.5 , cw * 0.15, ch * 0.2, "SKIP (10)");
    
}

void scaleCanvas() {
    ArrayList<OutputUnit> cur_robot = states.get(cur_state);
 
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

    int max_dim = (right_most_x > top_most_y) ? right_most_x : top_most_y;

    // padding, make sure even
    if(max_dim % 2 == 0) {
        max_dim += 2;
    } else {
        max_dim += 3;
    }

    NUM_W = max_dim;
    NUM_H = max_dim;
    num_w = max_dim;
    num_h = max_dim;


/*    println("rightmost: " + right_most_x);*/

}


void draw() {
    background (200, 200, 200);
    if(MODE == "OUTPUT") {
        scaleCanvas();
    }
    drawGrid(0, 0, DEFAULT_GRID_W, DEFAULT_GRID_H);
/*    drawMenu(0, 800, 800, 200);*/
    drawMenu(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, 
             DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);

    if(MODE == "OUTPUT") {
        drawOutputRobot(cur_state);
        drawFrameNumber();
/*        scaleCanvas();*/

        // play loop
        if(is_playing && cur_state < state_count - 1) {
            cur_state++;
            scaleCanvas();
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

void mouseClicked() {

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

    // OUTPUT //
    if (!is_playing && play_button.inBounds(mouseX, mouseY)) {
        is_playing = true;
    }

    if (is_playing && pause_button.inBounds(mouseX, mouseY)) {
        is_playing = false;
    }
    
    
    if (!is_playing && cur_state < state_count - 1 && stepf_button.inBounds(mouseX, mouseY)) {
        cur_state++;
        scaleCanvas();
    }

    if (!is_playing && cur_state > 0 && stepb_button.inBounds(mouseX, mouseY)) {
        cur_state--;
        scaleCanvas();
    }

    if (cur_state > 0 && restart_button.inBounds(mouseX, mouseY)) {
        cur_state = 0;
        scaleCanvas();
    }

    if (cur_state + 10 < state_count && skip_button.inBounds(mouseX, mouseY)) {
        cur_state += 10;
        scaleCanvas();
    }


    // UNIVERSAL //
    if (mode_button.inBounds(mouseX, mouseY)) {
        MODE = (MODE.equals("OUTPUT")) ? "INPUT_START" : "OUTPUT"; 
    }
}

// cx, cy is top left corner 
// (0, 0) in proceessing is top left fyi
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

    stroke(0, 80);
    line(cw / 2, 0, cw / 2, ch);
    line(0, ch/2, cw, ch/2);

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

void highlightGridSpace() {

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

void drawInputModules() {
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

void drawOutputRobot(int index) {
    ArrayList<OutputUnit> units = states.get(index);

    for(OutputUnit u: units) {
        u.render();
    }
}

void drawFrameNumber() {
    String s = "[" + (cur_state+1) + "/" + state_count + "]";

    // draw in bottom right of window
    textAlign(CENTER);
    fill(0);
    text(s, 0, .85 * DEFAULT_WINDOW_H, DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);
}

void drawMenu(int cx, int cy, int cw, int ch) {

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


// JSON nonsensj
void exportToJSON() {

}

void readOutputStates(String path_prefix) {

    states = new ArrayList<ArrayList<OutputUnit>>();
    String path = path_prefix + state_count + ".json";
    File f = new File(path);

    while(f.exists()) {
        
        // load json array of robots
        ArrayList<OutputUnit> units = new ArrayList<OutputUnit>();
        JSONArray robotArray = loadJSONArray(path);

        // for each robot, make a draw unit and add to state
        for(int i = 0; i < robotArray.size(); i++) {
            JSONObject robot = robotArray.getJSONObject(i);
            
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
