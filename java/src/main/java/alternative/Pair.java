package alternative;

public class Pair<A,B> {
    private final A left;
    private final B right;

    private Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public static<A,B> Pair<A,B> pair(A left, B right) {
        return new Pair<>(left, right);
    }

    public A left() {
        return left;
    }

    public B right() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", left, right);
    }

    @Override
    public boolean equals(Object other) {
        Pair<A,B> that = (Pair<A,B>) other;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("not yet implemented");
    }
}