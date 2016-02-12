public class Robot {
    private int x;
    private int y;

    public Robot() {
        this(0, 0);
    }

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public void drawContractedArms(int dir) {

        // how much of block length the unit width is
        double ratio = 4.0/5;

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

    public void drawUnit() {

        // calculate sizes
        int block_size =  width / num_w;
        double ratio = 4.0/5;
        int unit_width     = (int)((double)block_size * ratio);

        //TODO hacky, but flip y
        int mod_y = 20 - y - 1;

        // unit boundaries
        int left   = ((block_size * x)) + (block_size - unit_width)/2;
        int top    = ((block_size * mod_y)) + (block_size - unit_width)/2;
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);
        
        pushStyle();
            fill(51, 204, 255); // pretty blue
            stroke(1);
            rect(left, top, unit_width, unit_width);
        popStyle();
    }

    public void render() {

        // draw unit body
        drawUnit();

        // draw unit arms
        for(int dir = 0; dir < 4; dir++) {
/*            drawArm(dir, extensions[dir], connections[dir]);*/
            drawContractedArms(dir);
        }

    }
}
