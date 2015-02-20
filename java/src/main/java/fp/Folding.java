package fp;

import java.util.function.BiFunction;

public class Folding {
    public static<A,B> B foldRightRec(BiFunction<A, B, B> f, B v, List<A> xs) {
        return xs == null ? v : f.apply(xs.head, foldRightRec(f, v, xs.tail));
    }

    public static<A,B> B foldRightIter(BiFunction<A, B, B> f, B v, List<A> xs) {
        // todo: there must be a way, other than reversal
        return foldLeftIter(Prelude.flip(f), v, Prelude.reverse(xs));
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
}
