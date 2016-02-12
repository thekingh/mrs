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

void setup() {
    size(900, 900);
    modules = new ArrayList<Module>();
}

void drawGrid() {

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

void highlightGridSpace() {

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

void mouseClicked() {
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

void drawModules() {
    for(Module m : modules) {
        m.render();
    }
}

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

    saveJSONArray(jrs, "../../src/input.json");

}

void keyPressed() {
    int p = (int)'p';
    
    if(key == p) {
        produceJSON();
    }
}

void draw() {

    background(200, 200, 200);
    drawGrid();
    highlightGridSpace();
    drawModules();

}
