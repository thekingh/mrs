DrawWrapper drawer = new DrawWrapper(this);
Grid g;

void setup() {
    size(700, 700);
    drawer.setCanvasSize(700, 700);

    g = new Grid(50, 50);
    for(int i = 0; i < g.getWidth(); i++) {
        for(int j = 0; j < g.getHeight(); j++) {
            if((i + j) % 2 == 0) {
                g.setState(i, j, GridObject.VARM);
            } else {
                g.setState(i, j, GridObject.UNIT);
            }
        }
    }
}

void draw() {
    background(200, 200, 200);
    g.render();
}
