package src;
public class TestHelper {
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

    public static Robot makeBot(int[][] in) {
        return new Robot(orientArray(in), false); 
    }

    public static boolean verifyShape(Robot r, Robot s) {
        return r.equals(s);
    }

    public static void validateOutput(Robot r, Robot s) {
        if (verifyShape(r, s)) {
            System.out.println("OK");
        } else {
            throw new RuntimeException("NOT OK: Robot shapes do not match");
        }
    }

    private static boolean[][] orientArray(boolean[][] in) {
        return mirrorArray(invertArray(in));
    }
    private static boolean[][] orientArray(int[][] in) {
        return mirrorArray(invertArray(convertIntToBool(in)));
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

    private static boolean[][] convertIntToBool(int[][] in) {
        int w = in.length;
        int h = in[0].length;
        boolean [][] out =  new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[i][j] = (in[i][j] == 1);
            }
        }
        return out;
    }
}
