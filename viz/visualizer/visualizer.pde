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

Button start_button;
Button end_button;
Button clear_button;
Button save_button;

boolean valid_save      = true;
boolean start_connected = false;
boolean end_connected   = false;

ArrayList<InputModule> start_modules;
ArrayList<InputModule> end_modules;

void setup() {
    size(800, 1000);
/*    size(DEFAULT_WINDOW_W, DEFAULT_WINDOW_H);*/

    // make but don't draw input buttons
    init_buttons(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);

    start_modules = new ArrayList<InputModule>();
    end_modules = new ArrayList<InputModule>();
}

void init_buttons(int cx, int cy, int cw, int ch) {
    // button = new Button (x, y, w, h, "text");
    start_button = new Button(cx + cw * 0.125, cy + ch * 0.7, cw * 0.15, ch * 0.25, "START");
    end_button   = new Button(cx + cw * 0.325, cy + ch * 0.7, cw * 0.15, ch * 0.25, "END");
    clear_button = new Button(cx + cw * 0.525, cy + ch * 0.7, cw * 0.15, ch * 0.25, "CLEAR");
    save_button  = new Button(cx + cw * 0.725, cy + ch * 0.7, cw * 0.15, ch * 0.25, "SAVE");
}

void draw() {
    background (200, 200, 200);
    drawGrid(0, 0, DEFAULT_GRID_W, DEFAULT_GRID_H);
/*    drawMenu(0, 800, 800, 200);*/
    drawMenu(0, DEFAULT_GRID_H, DEFAULT_WINDOW_W - 1, 
             DEFAULT_WINDOW_H - DEFAULT_GRID_H - 1);
    highlightGridSpace();
    drawInputModules();

    valid_save = isValidSave();
}

void mouseClicked() {

    //MODULE CLICKING
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

    // BUTTON CLICKING
    if (start_button.inBounds(mouseX, mouseY)) {
        MODE = "INPUT_START";
    }

    if (end_button.inBounds(mouseX, mouseY)) {
        MODE = "INPUT_END";
    }

    if (clear_button.inBounds(mouseX, mouseY)) {
        start_modules.clear();
        end_modules.clear();
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

void drawMenu(int cx, int cy, int cw, int ch) {

    pushStyle();
        stroke(0, 0, 0);
        fill(230, 230, 230);
        rect(cx, cy, cw, ch);
        
        fill(255, 0, 0);
        textAlign(CENTER);
        //text(MODE, DEFAULT_WINDOW_W/2, 900);

        // draw buttons
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

        if(valid_save) {
            save_button.render(true);
        } else {
            save_button.render(false);
        }

        //TODO tell user why robot is broken

    popStyle();
}
