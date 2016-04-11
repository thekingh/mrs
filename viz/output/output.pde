ArrayList<ArrayList<DrawUnit>> states;
int stateCount = 0;
int num_w = 20;
int num_h = 20;
int curState = 0;

void setup() {
    states = new ArrayList<ArrayList<DrawUnit>>();
    readJData();

    size(900, 900);
}

void readJData() {

    String path = "states/json_states/state" + stateCount + ".json";
    File f = new File(path);

    while(f.exists()) {

        // load json array of robots
        ArrayList<DrawUnit> units = new ArrayList<DrawUnit>();
        JSONArray robotArray = loadJSONArray(path);

        // for each robot, make a draw unit and add to state
        for(int i = 0; i < robotArray.size(); i++) {
            JSONObject robot = robotArray.getJSONObject(i);
            
            DrawUnit u = new DrawUnit(robot.getInt("x"),
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
        println("added a state");
        stateCount++;

        // try getting next enumerated state
        path = "states/json_states/state" + stateCount + ".json";
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
        u.setXY(u.X() - 5, u.Y() - 5);
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
    String s = "[" + (curState+1) + "/" + stateCount + "]";

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
