package src;

public class TestAlgorithm {
    private static boolean[][] orientArray(boolean[][] in) {
        return mirrorArray(invertArray(in));
    }

    private static boolean[][] invertArray(boolean[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[h][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[j][i] = in[i][j];
            }
        }
        return out;
    }
    private static boolean[][] mirrorArray(boolean[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[i][j] = in[i][h - j - 1];
            }
        }
        return out;
    }

    public static boolean[][] getInputRobot() {
        boolean[][] moduleBools = {{true, false, true},
                                   {true, true, true}};
        return moduleBools;
    }

    public static boolean[][] getOutputRobot() {
        boolean[][] moduleBools = {{true, true, false},
                                   {true, true, true}};
        return moduleBools;
    }

    public static void printRobot(Robot r) {
        Module[][] modules = r.toModuleArray();
        int w = modules.length;
        int h = modules[0].length;
        for (int j = h - 1; j >= 0; j--) {
            for (int i = 0; i < w; i++) {
                if (modules[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(modules[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        boolean[][] in  = getInputRobot();
        boolean[][] out = getOutputRobot();

        Algorithm a = new Algorithm(in, out);
    }
}

