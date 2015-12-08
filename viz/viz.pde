//String data_path = "states/state";

ArrayList<ArrayList<DrawUnit>> states;
/*ArrayList<DrawUnit> units;*/
int stateCount = 0;
int num_w = 10;
int num_h = 10;
int curState = 0;

void setup() {
    states = new ArrayList<ArrayList<DrawUnit>>();
    readData();

    size(700, 700);
}

void readData() {

    String path = "states/state" + stateCount + ".rbt";
    File f = new File(path);

    while(f.exists()) {
    
        String[] lines = loadStrings(path); 
        ArrayList<DrawUnit> units = new ArrayList<DrawUnit>();

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

void drawGrid() {
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

void drawRobots(int index) {
    ArrayList<DrawUnit> units = states.get(index);
    for(DrawUnit u: units) {
        u.render();
    }
}

void keyPressed() {

    if(key == 'h' && curState > 0) {
        curState--;
    }

    if(key == 'l' && curState < stateCount-1) {
        curState++;
    }



}

void draw() {
    background(200, 200, 200);
    drawGrid();
    drawRobots(curState);
}
