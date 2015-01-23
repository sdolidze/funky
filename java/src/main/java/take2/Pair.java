package take2;

public class Pair<A,B> {
    public final A right;
    public final B left;

    private Pair(A right, B left) {
        this.right = right;
        this.left = left;
    }

    public static<A,B> Pair<A,B> pair(A right, B left) {
        return new Pair<>(right, left);
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", right, left);
    }

    @Override
    public boolean equals(Object other) {
        Pair<A,B> that = (Pair<A,B>) other;
        return this.right.equals(that.right) && this.left.equals(that.left);
    }
}