package fp;

import java.util.function.BiFunction;

public class Folding {
    private static int sumRight(List<Integer> xs) {
        return xs == null ? 0 : xs.head + sumRight(xs.tail);
    }

    public static<A,B> B foldRightRec(BiFunction<A, B, B> f, B v, List<A> xs) {
        return xs == null ? v : f.apply(xs.head, foldRightRec(f, v, xs.tail));
    }

    public static<A,B> B foldRightIter(BiFunction<A, B, B> f, B v, List<A> xs) {
        // todo: there must be a way, other than reversal
        return foldLeftIter(Prelude.flip(f), v, Prelude.reverse(xs));
    }

    private static int sumFoldRight(List<Integer> xs) {
        return foldRightRec((a, b) -> a + b, 0, xs);
    }

    private static int sumLeft(int acc, List<Integer> xs) {
        return xs == null ? acc : sumLeft(acc+xs.head, xs.tail);
    }

    public static<A,B> B foldLeftRec(BiFunction<B, A, B> f, B v, List<A> xs) {
        return xs == null ? v : foldLeftRec(f, f.apply(v, xs.head), xs.tail);
    }

    public static<A,B> B foldLeftIter(BiFunction<B,A,B> f, B v, List<A> xs) {
        B acc = v;
        while (xs != null) {
            acc = f.apply(acc, xs.head);
            xs = xs.tail;
        }
        return acc;
    }

    private static int sumFoldLeft(List<Integer> xs) {
        return foldLeftRec((a, b) -> a + b, 0, xs);
    }
}
