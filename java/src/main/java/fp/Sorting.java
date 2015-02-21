package fp;

import static fp.List.cons;
import static fp.Pair.pair;
import static fp.Prelude.*;

public class Sorting {
    public static<T> Pair<List<T>,List<T>> halve(List<T> xs) {
        int length = length(xs) / 2;
        return pair(take(length, xs), drop(length, xs));
    }

    /**
     * make it tail recursive? iterative? in place?
     * merge two sorted lists
     */
    public static<T extends Comparable<T>> List<T> merge(List<T> xs, List<T> ys) {
        if (xs == null) {
            return ys;
        } else if (ys == null) {
            return xs;
        } else if (lt(xs.head, ys.head)) {
            return cons(xs.head, merge(xs.tail, ys));
        } else {
            return cons(ys.head, merge(xs, ys.tail));
        }
    }

    public static<T extends Comparable<T>> List<T> msort(List<T> xs) {
        if (length(xs) <= 1) {
            return xs;
        }
        Pair<List<T>,List<T>> pair = halve(xs);
        return merge(msort(pair.left), msort(pair.right));
    }

    public static<T> List<Pair<T,T>> pairs(List<T> xs) {
        return zip(xs, xs.tail);
    }

    public static<T extends Comparable<T>> boolean isSorted(List<T> xs) {
        // todo: write this iteratively
        return all(x -> lt(x.left, x.right), pairs(xs));
    }

    private static<T extends Comparable<T>> boolean lt(T left, T right) {
        return left.compareTo(right) < 0;
    }

    private static<T extends Comparable<T>> boolean gt(T left, T right) {
        return left.compareTo(right) > 0;
    }

    private static<T extends Comparable<T>> boolean gte(T left, T right) {
        return left.compareTo(right) >= 0;
    }

}
