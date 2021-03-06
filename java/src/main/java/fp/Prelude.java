package fp;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static fp.List.cons;
import static fp.List.list;
import static fp.Pair.pair;

/**
 * todo: every function needs a unit test ;)
 * This class is used as a namespace for standard functions.
 * Name inspired by Haskell.
 */
public class Prelude {
    public static<T> T head(List<T> xs) {
        return xs.head;
    }

    public static<T> List<T> tail(List<T> xs) {
        return xs.tail;
    }

    public static<A> A last(List<A> xs) {
        if (xs.tail == null) {
            return xs.head;
        } else {
            return last(xs.tail);
        }
    }

    public static<A> List<A> init(List<A> xs) {
        if (xs.tail == null) {
            return null;
        } else {
            return cons(xs.head, init(xs.tail));
        }
    }

    public static<T> List<T> reverse(List<T> xs) {
        return CopyReverse.reverseIter(xs);
    }

    public static<A,B> B foldRight(BiFunction<A, B, B> f, B v, List<A> xs) {
       return Folding.foldRightIter(f, v, xs);
    }

    public static<A,B> B foldLeft(BiFunction<B, A, B> f, B v, List<A> xs) {
        return Folding.foldLeftIter(f, v, xs);
    }

    public static<A,B> List<B> scanRight(BiFunction<B, A, B> f, B v, List<A> xs) {
        throw new NotImplementedException();
    }

    public static<A,B> List<B> scanLeft(BiFunction<B, A, B> f, B v, List<A> xs) {
        return Folding.scanLeftIter(f, v, xs);
    }

    public static<A> A foldLeft1(BinaryOperator<A> f, List<A> xs) {
        return foldLeft(f, xs.head, xs.tail);
    }

    public static<A> A foldRight1(BinaryOperator<A> f, List<A> xs) {
        /* can be implemented smarter way:
         * in Haskell Prelude:
         * ---
         * foldr1           :: (a -> a -> a) -> [a] -> a
         * foldr1 f [x]     =  x
         * foldr1 f (x:xs)  =  f x (foldr1 f xs)
         * foldr1 _ []      =  error "Prelude.foldr1: empty list"
         */

        return foldRight(f, last(xs), init(xs));
    }

    public static<A,B> List<B> map(Function<A, B> f, List<A> xs) {
        // todo: also implement this iteratively
        if (xs == null) {
            return null;
        } else {
            return cons(f.apply(xs.head), map(f, xs.tail));
        }

//        return foldRight((x, ys) -> cons(f.apply(x), ys), null, xs);
    }

    public static<A> List<A> filter(Predicate<A> p, List<A> xs) {
        return foldRight((y, ys) -> p.test(y) ? cons(y, ys) : ys, null, xs);
    }

    public static<A> List<A> extend(List<A> xs, List<A> ys) {
        return foldRight(List::cons, ys, xs);
    }

    public static<A> List<A> append(A x, List<A> xs) {
        return extend(xs, list(x));
    }

    public static<A,B,C> Function<B,C> partial(BiFunction<A,B,C> f, A a) {
        return b -> f.apply(a, b);
    }

    public static<A,B,C> BiFunction<B,A,C> flip(BiFunction<A,B,C> f) {
        return (b,a) -> f.apply(a, b);
    }

    public static<A> boolean contains(A x, List<A> xs) {
        if (xs == null) {
            return false;
        } else {
            return x.equals(xs.head) ? true : contains(x, xs.tail);
        }
    }

    public static<A> List<A> flatten(List<List<A>> xss) {
        return foldRight(Prelude::extend, null, xss);
    }

    public static<A,B> List<B> flatMap(Function<A,List<B>> f, List<A> xs) {
        return flatten(map(f, xs));
    }

    public static<A> List<A> take(int n, List<A> xs) {
        return (n == 0 || xs == null) ? null : cons(xs.head, take(n - 1, xs.tail));
    }

    public static<A> List<A> drop(int n, List<A> xs) {
        if (n == 0) {
            return xs;
        } else if (xs == null) {
            return null;
        } else {
            return drop(n-1, xs.tail);
        }
    }

    public static<A> List<A> takeWhile(Predicate<A> p, List<A> xs) {
        if (xs == null) {
            return null;
        } else {
            return p.test(xs.head) ? cons(xs.head, takeWhile(p, xs.tail)) : null;
        }
    }

    public static<A> List<A> dropWhile(Predicate<A> p, List<A> xs) {
        if (xs == null) {
            return null;
        } else {
            return p.test(xs.head) ? dropWhile(p, xs.tail) : xs;
        }
    }

    public static<A> Function<A,A> id() {
        return a -> a;
    }

    public static<A,B,C> Function<A,C> compose(Function<B,C> f, Function<A,B> g) {
        return a -> f.apply(g.apply(a));
    }

    public static<A,B,C> Function<A, Function<B,C>> curry(BiFunction<A,B,C> f) {
        return a -> b -> f.apply(a, b);
    }

    public static<A,B,C> BiFunction<A,B,C> uncurry(Function<A, Function<B,C>> f) {
        return (a, b) -> f.apply(a).apply(b);
    }

    public static List<Integer> sort(List<Integer> xs) {
        if (xs == null) {
            return null;
        } else {
            List<Integer> less = filter(x -> x <= xs.head, xs.tail);
            List<Integer> more = filter(x -> x >  xs.head, xs.tail);
            return flatten(list(sort(less), list(xs.head), sort(more)));
        }
    }

    public static<T> boolean equals(List<T> xs, List<T> ys) {
        if (xs == null && ys == null) {
            return true;
        } else if (xs == null || ys == null) {
            return false;
        } else {
            return xs.head.equals(ys.head) && equals(xs.tail, ys.tail);
        }
    }

    public static<T> int length(List<T> xs) {
        return foldLeft((acc,x) -> acc+1, 0, xs);
    }

    public static<T> List<T> unfold(Predicate<T> stop, Function<T,T> h, Function<T,T> t, T seed) {
        // int2binLittleEndian = unfold (==0) (`mod`2) (`div`2)
        if (stop.test(seed)) {
            return null;
        } else {
            return cons(h.apply(seed), unfold(stop, h, t, t.apply(seed)));
        }
    }

    public static<T> List<List<T>> partition(int n, List<T> ys) {
        return unfold(xs -> xs == null, xs -> take(n, xs), xs -> drop(n, xs), ys);
    }

    public static<T> List<Pair<T,T>> zip(List<T> xs, List<T> ys) {
        if (xs == null || ys == null) {
            return null;
        } else {
            return cons(pair(xs.head, ys.head), zip(xs.tail, ys.tail));
        }
    }

    public static<A,B,C> List<C> zipWith(BiFunction<A,B,C> f, List<A> as, List<B> bs) {
        throw new RuntimeException("not yet implemented");
    }

    public static<T> boolean all(Predicate<T> p, List<T> xs) {
        return foldLeft((x, y) -> x && y, true, map(p::test, xs));
    }

    public static<T> boolean any(Predicate<T> p, List<T> xs) {
        return foldLeft((x, y) -> x || y, false, map(p::test, xs));
    }

    public static<T> Pair<List<T>, List<T>> partitionBy(Predicate<T> p, List<T> xs) {
        if (xs == null) {
            return pair(null, null);
        } else {
            Pair<List<T>, List<T>> prev = partitionBy(p, xs.tail);
            if (p.test(xs.head)) {
                return pair(cons(xs.head, prev.left), prev.right);
            } else {
                return pair(prev.left, cons(xs.head, prev.right));
            }
        }
    }
}
