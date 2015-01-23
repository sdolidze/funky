package take2;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static take2.List.list;
import static take2.List.cons;

/**
 * This class is used as a namespace for standard functions. Name inspired by Haskell.
 */
public class Prelude {

    public static<T> T head(List<T> xs) {
        return xs.head;
    }

    public static<T> List<T> tail(List<T> xs) {
        return xs.tail;
    }

    public static<T> List<T> reverse(List<T> xs) {
        return CopyReverse.reverseIter(xs);
    }

    public static<A> Iterator<A> replicate(A a) {
        return new Iterator<A>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                return a;
            }
        };
    }

    public static<A,B> B foldRight(BiFunction<A, B, B> f, B v, List<A> xs) {
       return Folding.foldRightIter(f, v, xs);
    }

    public static<A,B> B foldLeft(BiFunction<B, A, B> f, B v, List<A> xs) {
        return Folding.foldLeftIter(f, v, xs);
    }

    public static<A> A foldLeft1(BinaryOperator<A> f, List<A> xs) {
        return foldLeft(f, xs.head, xs.tail);
    }

    public static<A> A foldRight1(BinaryOperator<A> f, List<A> xs) {
        return foldRight(f, xs.head, xs.tail);
    }

    public static<A,B> List<B> map(Function<A, B> f, List<A> xs) {
        return foldRight((x, ys) -> cons(f.apply(x), ys), null, xs);
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

    public static<A> boolean containsList(List<A> xs, List<A> ys) {
        if (xs == null) {
            return true;
        } else if (ys == null) {
            return false;
        } else {
            return xs.head.equals(ys.head) ? containsList(xs.tail, ys.tail) : containsList(xs, ys.tail);
        }
    }

    public static<A> boolean isPrefix(List<A> xs, List<A> ys) {
        if (xs == null) {
            return true;
        } else if (ys == null) {
            return false;
        } else {
            return xs.head.equals(ys.head) && isPrefix(xs.tail, ys.tail);
        }
    }

    public static<A,B> List<B> flatMap(Function<A,List<B>> f, List<A> xs) {
        return flatten(map(f, xs));
    }

    public static<A> List<List<A>> powerSet(List<A> xs) {
        if (xs == null) {
            return null;
        } else {
            List<List<A>> prev = powerSet(xs.tail);
            return extend(prev, map(y -> cons(xs.head, y), prev));
        }
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

    public static Integer sum(List<Integer> xs) {
        return foldLeft((x,y) -> x+y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return foldLeft((x,y) -> x*y, 1, xs);
    }


    public static<A> List<A> unique(List<A> xs) {
        if (xs == null) {
            return null;
        } else {
            return contains(xs.head, xs.tail) ? unique(xs.tail) : cons(xs.head, unique(xs.tail));
        }
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

}
