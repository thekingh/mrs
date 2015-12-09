public class DrawUnit {
    
    int x,y;
    int[]  connections;
    int[]  extensions;

    public DrawUnit() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public DrawUnit(String s) {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

/*    public DrawUnit(int x, int y, int c0, int e0, int c1, int e1, int c2, int e2, int c3, int e3) {*/
    public DrawUnit(int x, int y, int e0, int c0, int e1, int c1, int e2, int c2, int e3, int c3) {
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

    public void render() {

        //TODO get them to touch
        // do some maths, center on axes
        int block_size =  width / num_w;
        double ratio = 4.0/5;
        int unit_width     = (int)((double)block_size * ratio);
        int margin         = (int)(((1 -  ratio)/2) * (double)block_size);
        int extended_len   = margin + block_size/2;
        int contracted_len = margin;
        int disconnect     = margin/2;

        int left   = ((block_size * (num_w/2 + x)) + (block_size - unit_width)/2);
        int top    = ((block_size * (num_h/2 - y)) + (block_size - unit_width)/2);
        int right  = (left + unit_width);
        int bottom = (top  + unit_width);
        

        // draw rectangle, red for right now
        fill(51, 204, 255);
        rect(left, top, unit_width, unit_width);
        fill(255, 255, 255);

        //draw arms LOL
        //TODO this code is ugly and I hate it 

        //top
        stroke(0, 255);
        strokeWeight(1.5);
        if(this.extensions[0] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, top - extended_len);
            line(left + (unit_width/4), top - extended_len, right - unit_width/4, top - extended_len);
        } else {
            if(connections[0] == 1) {
                line(left + (unit_width/2), top, left + unit_width/2, top - contracted_len);
                line(left + (unit_width/4), top - contracted_len, right - unit_width/4, top - contracted_len);
            } else {
                line(left + (unit_width/2), top, left + unit_width/2, top - contracted_len + disconnect);
                line(left + (unit_width/4), top - contracted_len + disconnect, right - unit_width/4, top - contracted_len + disconnect);
            }
        }

        //bottom
        if(this.extensions[2] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, bottom + extended_len);
            line(left + (unit_width/4), bottom + extended_len, right - unit_width/4, bottom + extended_len);
        } else {
            if(connections[2] == 1) {
                line(left + (unit_width/2), bottom, left + unit_width/2, bottom + contracted_len);
                line(left + (unit_width/4), bottom + contracted_len, right - unit_width/4, bottom + contracted_len);
            } else {
                line(left + (unit_width/2), bottom, left + unit_width/2, bottom + contracted_len - disconnect);
                line(left + (unit_width/4), bottom + contracted_len - disconnect, right - unit_width/4, bottom + contracted_len - disconnect);
            }
        }

        //left
        if(this.extensions[3] == 1) {
                line(left, top + (unit_width/2), left - extended_len , top + unit_width/2 );
                line(left - extended_len, top + (unit_width/4), left - extended_len, bottom - (unit_width/4));
        } else {
            if(connections[3] == 1) {
                line(left, top + (unit_width/2), left - contracted_len , top + unit_width/2 );
                line(left - contracted_len, top + (unit_width/4), left - contracted_len, bottom - (unit_width/4));
            } else {
                line(left, top + (unit_width/2), left - contracted_len + disconnect , top + unit_width/2 );
                line(left - contracted_len + disconnect, top + (unit_width/4), left - contracted_len + disconnect, bottom - (unit_width/4));
            }
        }

        //right
        if(this.extensions[1] == 1) {
            line(right, top + (unit_width/2), right + extended_len , top + unit_width/2 );
            line(right + extended_len, top + (unit_width/4), right + extended_len, bottom - (unit_width/4));
        } else {
            if(connections[1] == 1) {
                line(right, top + (unit_width/2), right + contracted_len , top + unit_width/2 );
                line(right + contracted_len, top + (unit_width/4), right + contracted_len, bottom - (unit_width/4));
            } else {
                line(right, top + (unit_width/2), right + contracted_len - disconnect, top + unit_width/2 );
                line(right + contracted_len - disconnect, top + (unit_width/4), right + contracted_len - disconnect, bottom - (unit_width/4));
            }
        }
    }
}
