DrawWrapper drawer = new DrawWrapper(this);
Grid g;

void setup() {
    size(700, 700);

    g = new Grid(50, 50);
    for(int i = 0; i < g.getWidth(); i++) {
        for(int j = 0; j < g.getHeight(); j++) {
            g.setState(i, j, ((i +j )  % 2));
        }
    }
}

void draw() {
    background(200, 200, 200);
    g.render();
}
