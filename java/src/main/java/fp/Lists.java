package fp;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static fp.List.*;
import static fp.List.cons;
import static fp.List.empty;
import static fp.Pair.pair;

/**
 * @author Sandro Dolidze
 */
public class Lists {
    public static<T> boolean isSame(List<T> xs, List<T> ys) {
        if (xs.isEmpty() && ys.isEmpty()) {
            return true;
        } else if (xs.isEmpty() || ys.isEmpty()) {
            return false;
        } else {
            return xs.head().equals(ys.head()) && isSame(xs.tail(), ys.tail());
        }
    }

    public static<A,B> B foldr(BiFunction<A,B,B> r, B v, List<A> xs) {
        if (xs.isEmpty()) {
            return v;
        }
        return r.apply(xs.head(), foldr(r, v, xs.tail()));
    }

    public static<A,B> B foldl(BiFunction<B,A,B> f, B v, List<A> xs) {
        return xs.isEmpty() ? v : foldl(f, f.apply(v, xs.head()), xs.tail());
    }

    public static<T> List<T> concat(List<T> left, List<T> right) {
        return foldr(List::cons, right, left);
    }

    public static<T> Integer length(List<T> xs) {
        return foldr((x,acc) -> acc+1, 0, xs);
    }

    public static<T> List<T> append(T x, List<T> xs) {
        return xs.isEmpty() ? cons(x, empty()) : cons(xs.head(), append(x, xs.tail()));
    }


    public static<T> List<T> take(int n, List<T> xs) {
        if (n == 0 || xs.isEmpty()) {
            return empty();
        } else {
            return cons(xs.head(), take(n-1, xs.tail()));
        }
    }

    public static<T> List<T> drop(int n, List<T> xs) {
        if (n == 0) {
            return xs;
        } else if (xs.isEmpty()) {
            return empty();
        } else {
            return drop(n - 1, xs.tail());
        }
    }

    public static<T> Pair<List<T>,List<T>> halve(List<T> xs) {
        int length = length(xs) / 2;
        return pair(take(length, xs), drop(length, xs));
    }

    /**
     * merge two sorted lists
     */
    public static<T extends Comparable<T>> List<T> merge(List<T> xs, List<T> ys) {
        if (xs.isEmpty()) {
            return ys;
        } else if (ys.isEmpty()) {
            return xs;
        } else if (lt(xs.head(), ys.head())) {
            return cons(xs.head(), merge(xs.tail(), ys));
        } else {
            return cons(ys.head(), merge(xs, ys.tail()));
        }
    }

    public static<T extends Comparable<T>> List<T> msort(List<T> xs) {
        if (length(xs) <= 1) {
            return xs;
        }
        Pair<List<T>,List<T>> pair = halve(xs);
        return merge(msort(pair.right()), msort(pair.left()));
    }

    public static<T> List<T> filter(Predicate<T> p, List<T> xs) {
        return foldr((y, ys) -> p.test(y) ? cons(y, ys) : ys, empty(), xs);
    }

    public static<T extends Comparable<T>> List<T> qsort(List<T> xs) {
        if (xs.isEmpty()) {
            return xs;
        }
        List<T> less = filter(x ->  lt(x, xs.head()), xs.tail());
        List<T> more = filter(x -> gte(x, xs.head()), xs.tail());
        return flatten(list(qsort(less), list(xs.head()), qsort(more)));
    }

    public static<A,B> List<B> map(Function<A, B> f, List<A> xs) {
        return foldr((y, ys) -> cons(f.apply(y), ys), empty(), xs);
    }

    public static<T> List<T> reverse(List<T> ys) {
        return foldl((x,y) -> cons(y,x), empty(), ys);
    }

    private static<A,B,C> BiFunction<B,A,C> flip(BiFunction<A,B,C> f) {
        return (b, a) -> f.apply(a, b);
    }

    public static<A,B,C> Function<A, Function<B,C>> curry(BiFunction<A,B,C> f) {
        return a -> b -> f.apply(a, b);
    }

    public static<A,B,C> BiFunction<A,B,C> uncurry(Function<A, Function<B,C>> f) {
        return (a, b) -> f.apply(a).apply(b);
    }

    public static Integer sum(List<Integer> xs) {
        return foldr((x,y)->x+y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return foldr((x,y)->x*y, 1, xs);
    }


    public static<T> List<T> takeWhile(final Predicate<T> p, List<T> ys) {
        return foldr((x, xs) -> p.test(x) ? cons(x, xs) : empty(), empty(), ys);
    }

    public static<T> List<T> dropWhile(final Predicate<T> p, List<T> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else if (p.test(xs.head())) {
            return dropWhile(p, xs.tail());
        } else {
            return xs;
        }
    }

    public static<T> List<T> unfold(Predicate<T> stop, Function<T,T> h, Function<T,T> t, T seed) {
        // int2binLittleEndian = unfold (==0) (`mod`2) (`div`2)
        if (stop.test(seed)) {
            return empty();
        }
        return cons(h.apply(seed), unfold(stop, h, t, t.apply(seed)));
    }

    public static List<Integer> int2dec(int n) {
        if (n == 0) {
            return empty();
        }
        return append(n % 10, int2dec(n / 10));
    }

    public static Integer dec2int(List<Integer> xs) {
        return foldl((a, x) -> 10*a + x, 0, xs);
    }


    public static<T> List<List<T>> partition(int n, List<T> ys) {
        return unfold(List::isEmpty, xs -> take(n, xs), xs -> drop(n, xs), ys);
    }

    public static<T> List<T> flatten(List<List<T>> xss) {
        return foldr(Lists::concat, empty(), xss);
    }

    public static<T> List<Pair<T,T>> zip(List<T> xs, List<T> ys) {
        if (xs.isEmpty() || ys.isEmpty()) {
            return empty();
        } else {
            return cons(pair(xs.head(), ys.head()), zip(xs.tail(), ys.tail()));
        }
    }

    public static<T> List<Pair<T,T>> pairs(List<T> xs) {
        return zip(xs, xs.tail());
    }

    public static<T> boolean all(Predicate<T> p, List<T> xs) {
        return foldr((x,y) -> x && y, true, map(p::test, xs));
    }

    public static boolean isSorted(List<? extends Comparable<?>> xs) {
        return all(x -> lt(x.right(), x.left()), pairs(xs));
    }

    public static boolean lt(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static boolean gte(Comparable a, Comparable b) {
        return a.compareTo(b) >= 0;
    }

    public static<T> Pair<List<T>, List<T>> partitionBy(Predicate<T> p, List<T> xs) {
        if (xs.isEmpty()) {
            return pair(empty(), empty());
        } else {
            Pair<List<T>, List<T>> prev = partitionBy(p, xs.tail());
            if (p.test(xs.head())) {
                return pair(cons(xs.head(), prev.right()), prev.left());
            } else {
                return pair(prev.right(), cons(xs.head(), prev.left()));
            }
        }
    }

    public static<A,B,C> Function<A,C> compose(Function<B,C> f, Function<A,B> h) {
        return a -> f.apply(h.apply(a));
    }
}
