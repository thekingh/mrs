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
        double ratio = 4.0/5;

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
        double ratio = 4.0/5;
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
