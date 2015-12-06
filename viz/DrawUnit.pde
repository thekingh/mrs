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

    public DrawUnit(int x, int y, int c0, int e0, int c1, int e1, int c2, int e2, int c3, int e3) {
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

        // do some maths, center on axes
        int block_size =  width / num_w;
        int unit_width = block_size/2;

        int extended_len   = unit_width/2;
        int contracted_len = unit_width/8;

        int left   =  (block_size * (num_w/2 + x)) + unit_width/2;
        int top    =  (block_size * (num_h/2 - y)) + unit_width/2;
        int right  =  left + unit_width;
        int bottom =  top  + unit_width;
        


        //draw arms LOL
        //TODO this code is ugly and I hate it 

        //top
        stroke(0, 255);
        strokeWeight(2);
        if(this.extensions[0] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, top - extended_len);
            line(left + (unit_width/4), top - extended_len, right - unit_width/4, top - extended_len);
        } else {
            line(left + (unit_width/2), top, left + unit_width/2, top - contracted_len);
            line(left + (unit_width/4), top - contracted_len, right - unit_width/4, top - contracted_len);
        }

        //bottom
        if(this.extensions[2] == 1) {
            line(left + (unit_width/2), top, left + unit_width/2, bottom + extended_len);
            line(left + (unit_width/4), bottom + extended_len, right - unit_width/4, bottom + extended_len);
        } else {
            line(left + (unit_width/2), bottom, left + unit_width/2, bottom + contracted_len);
            line(left + (unit_width/4), bottom + contracted_len, right - unit_width/4, bottom + contracted_len);
        }

        //left
        if(this.extensions[3] == 1) {
            line(left, top + (unit_width/2), left - extended_len , top + unit_width/2 );
            line(left - extended_len, top + (unit_width/4), left - extended_len, bottom - (unit_width/4));
        } else {
            line(left, top + (unit_width/2), left - contracted_len , top + unit_width/2 );
            line(left - contracted_len, top + (unit_width/4), left - contracted_len, bottom - (unit_width/4));
        }

        //right
        if(this.extensions[1] == 1) {
            line(right, top + (unit_width/2), right + extended_len , top + unit_width/2 );
            line(right + extended_len, top + (unit_width/4), right + extended_len, bottom - (unit_width/4));
        } else {
            line(right, top + (unit_width/2), right + contracted_len , top + unit_width/2 );
            line(right + contracted_len, top + (unit_width/4), right + contracted_len, bottom - (unit_width/4));
        }

        strokeWeight(1);

        // draw rectangle, red for right now
        fill(255, 0, 0);
        rect(left, top, (width / num_w)/2, (height / num_h)/2);
        fill(255, 255, 255);

    }





}
