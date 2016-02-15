package rutils;

/**
 * Class describing a Pair of Objects
 *
 * @author Casey Gowrie
 * @author Kabir Singh
 * @author Alex Tong
 * @version 1.0
 * @since 2/14/16
 */
public class Pair<A, B> {
    public final A a;
    public final B b;
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
