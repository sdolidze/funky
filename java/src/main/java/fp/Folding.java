package fp;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.BiFunction;

import static fp.List.cons;
import static fp.List.list;

public class Folding {
    public static<A,B> B foldRightRec(BiFunction<A, B, B> f, B v, List<A> xs) {
        return xs == null ? v : f.apply(xs.head, foldRightRec(f, v, xs.tail));
    }

    public static<A,B> B foldRightIter(BiFunction<A, B, B> f, B v, List<A> xs) {
        // todo: there must be a way, other than reversal
        return foldLeftIter(Prelude.flip(f), v, Prelude.reverse(xs));
    }

    public static<A,B> List<B> scanRightRec(BiFunction<A, B, B> f, B v, List<A> xs) {
        if (xs == null) {
            return list(v);
        } else {
            List<B> ys = scanRightRec(f, v, xs.tail);
            return cons(f.apply(xs.head, ys.head), ys);
        }
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

    public static<A,B> List<A> scanLeftRec(BiFunction<A, B, A> f, A v, List<B> xs) {
        if (xs == null) {
            return list(v);
        } else {
            return cons(v, scanLeftRec(f, f.apply(v, xs.head), xs.tail));
        }
    }

    public static<A,B> List<A> scanLeftIter(BiFunction<A, B, A> f, A v, List<B> xs) {
        throw new NotImplementedException();
    }
}
