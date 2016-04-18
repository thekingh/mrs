/*
    TODO
        - make snapping only to 2x2 boxes, no invalid even allowed
        - clean up code omg this is so hacky
        - make json export naming scheme
*/

/** 
 * Input visualization 
 * <p> Allows users to click on grid spaces and add modules to a graph
 *     and generate a json file of the grid state.
 *
 *  @author Casey Gowrie
 *  @author Alex Tong
 *  @author Kabir Singh
 *  
 *  @version 1.0
 */

ArrayList<InputModule> modules;
String INPUT_PATH = "states/input.json";
int num_w = 20;
int num_h = 20;

/** 
 * One time initial function to initalize the canvas and array of InputModules 
 *
 */
void setup() {
    size(900, 900);
    modules = new ArrayList<InputModule>();
}

/**
 * Draws grid with block-sized tiles, module-sized tiles, and the x and y
 * axes (opacity in increasing order).
 */
void drawGrid() {

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

/**
 * Given a coordinate, determine if a module can be placed there
 * @param x x coordinate of mouse/etc (0 leftmost tiles)
 * @param y y coordinate of mouse/etc (0 bottommost tiles)
 * TODO yeah I know, I know (entire coordinate system should be retooled)
 */
public boolean isValidPlacement(int x, int y) {

    // Is on even gridline (snapping)
    if( (x % 2 == 1)   || (y % 2 == 1)){  
        return false;
    }

    for(InputModule m: modules) {
        // Check to see if a module placed at (x, y) wouldn't overlap with existing modules.
        if((x > m.X() - 2) && (x < m.X() + 2) && 
           (y > m.Y() - 2) && (y < m.Y() + 2)) { 
                return false;
        }
    }

    return true;

}

/**
 * Highlight the grid space being hovered over w/ correct coloring.
 */
void highlightGridSpace() {

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

/**
 * Event trigger function (equiv onMouseClick()), tries to add module if on 
 * valid coordinate for placement.
 */
void mouseClicked() {
    int sqr_len = width/num_w;

    int x = (mouseX / sqr_len);
    int y = 20 - (mouseY / sqr_len) - 1; 

/*    print("clicked on coordinate: ");*/
/*    println("(" + x + " ," + y + ")");*/

    if (isValidPlacement(x, y)) {
        InputModule nm = new InputModule(x, y);
        modules.add(nm);
    }

}

/**
 * Loops through and render all modules.
 */
void drawInputModules() {
    for(InputModule m : modules) {
        m.render();
    }
}

/**
 *  Generate a JSON file of the state of the current grid 
 *  and write to /viz/input/states/input.json
 */
void produceJSON() {    
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

    saveJSONArray(jrs, INPUT_PATH);

}

/**
 * Event listner function - currently only listens for 'p' key press.
 * 
 */
void keyPressed() {
    int p = (int)'p';
    
    if(key == p) {
        produceJSON();
    }
}

/** 
 * Processing function called several times a second; currently
 * draws grid, modules, and highlights spaces.
 */
void draw() {

    // "clear" the canvas
    background(200, 200, 200);
    drawGrid();
    highlightGridSpace();
    drawInputModules();

}
