String data_path = "states/state";

ArrayList<ArrayList<DrawUnit>> states;
int stateCount = 0;
int num_w = 20;
int num_h = 20;
int curState = 0;

void setup() {
    states = new ArrayList<ArrayList<DrawUnit>>();
    readData();

    size(700, 700);
}

void readData() {

    // read in states from states/ directory
    String path = "states/state" + stateCount + ".rbt";
    File f = new File(path);

    // check to see if state exists, allows for dynamic num states
    while(f.exists()) {
    
        String[] lines = loadStrings(path); 
        ArrayList<DrawUnit> units = new ArrayList<DrawUnit>();

        // TODO get rid of \n at end or don't read it at all
        for(int i = 1; i < lines.length - 1; i++) {
            String[] robotString = split(lines[i], ",");

            // convert read in strings to ints
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

        // try getting next enumerated state
        path = "states/state" + stateCount + ".rbt";
        f = new File(path);
    }
}

void drawGrid() {

    // transparency
    stroke(0, 20);

    // grid lines
    // vertical lines
    for(int i = 0; i < num_w; i++) {
        line((width / num_w) * i, 0, (width / num_w) * i, height);
    }

    // horizontal lines
    for(int i = 0; i < num_h; i++) {
        line(0, (height / num_h) * i, width, (height / num_h) * i);
    }

    //axes
    stroke(0, 50);
    line(width / 2, 0, width / 2, height);
    line(0, height/2, width, height/2);

    stroke(0, 100);

}

void drawRobots(int index) {

    // for every state
    ArrayList<DrawUnit> units = states.get(index);

    // draw every unit
    for(DrawUnit u: units) {
        u.render();
    }
}

void keyPressed() {

    // increment state
    if(key == 'l' && curState < stateCount-1) {
        curState++;
    }

    // decrement state
    if(key == 'h' && curState > 0) {
        curState--;
    }

}

void drawFrameNumber() {

    // [cur / total]
    String s = "[" + curState + "/" + stateCount + "]";

    // draw in bottom right of window
    textAlign(RIGHT);
    fill(0);
    text(s, 0, .95 * height, width, height);
}

void draw() {

    background(200, 200, 200);
    drawGrid();
    drawRobots(curState);
    drawFrameNumber();

}
