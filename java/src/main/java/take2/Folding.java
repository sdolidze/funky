package take2;

import java.util.function.BiFunction;

import static take2.List.list;

/**
 * Created by sandro on 1/10/15.
 */
public class Folding {
    public static int sumRight(List<Integer> xs) {
        return xs == null ? 0 : xs.head + sumRight(xs.tail);
    }

    public static<A,B> B foldRight(BiFunction<A,B,B> f, B v, List<A> xs) {
        return xs == null ? v : f.apply(xs.head, foldRight(f, v, xs.tail));
    }

    private static<A> List<A> reverse(List<A> xs) {
        return null;
    }

    public static<A,B> B foldRightIter(BiFunction<A,B,B> f, B v, List<A> xs) {
        B acc = v;
        for (A x: reverse(xs)) {
            acc = f.apply(x, acc);
        }
        return acc;
    }

    public static int sumFoldRight(List<Integer> xs) {
        return foldRight((a,b) -> a+b, 0, xs);
    }

    public static int sumLeft(int acc, List<Integer> xs) {
        return xs == null ? acc : sumLeft(acc+xs.head, xs.tail);
    }

    public static<A,B> B foldLeft(BiFunction<B,A,B> f, B v, List<A> xs) {
        return xs == null ? v : foldLeft(f, f.apply(v, xs.head), xs.tail);
    }

    public static<A,B> B foldLeftIter(BiFunction<B,A,B> f, B v, List<A> xs) {
        B acc = v;
        for (A x: xs) {
            acc = f.apply(acc, x);
        }
        return acc;
    }

    public static int sumFoldLeft(List<Integer> xs) {
        return foldLeft((a,b) -> a+b, 0, xs);
    }

    public static void main(String[] args) {
        System.out.println(sumFoldRight(list(1, 2, 3)));
        System.out.println(sumFoldLeft(list(1, 2, 3)));
    }
}
