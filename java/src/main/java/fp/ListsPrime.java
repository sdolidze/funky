package fp;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static fp.List.*;

/**
 * @author Sandro Dolidze
 */
public class ListsPrime {
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

    public static<A,B> B foldr(BiFunction<A, B, B> f, B v, List<A> xs) {
        return xs.isEmpty() ? v : f.apply(xs.head(), foldr(f, v, xs.tail()));
    }

    public static<A,B> B foldl(BiFunction<B,A,B> f, B v, List<A> xs) {
        return xs.isEmpty() ? v : foldl(f, f.apply(v, xs.head()), xs.tail());
    }

    public static<A,B> B foldLeft(BiFunction<B,A,B> f, B v, List<A> xs) {
        B acc = v;
        for (A x: xs) {
            acc = f.apply(acc, x);
        }
        return acc;
    }

    public static<A> A foldLeft1(BinaryOperator<A> f, List<A> xs) {
        return foldLeft(f, xs.head(), xs.tail());
    }

    public static<A,B> List<B> map(Function<A, B> f, List<A> xs) {
        return foldr((x, ys) -> cons(f.apply(x), ys), empty(), xs);
    }

    public static<A> List<A> filter(Predicate<A> p, List<A> xs) {
        return foldr((y, ys) -> p.test(y) ? cons(y, ys) : ys, empty(), xs);
    }

    public static<A> List<A> extend(List<A> xs, List<A> ys) {
        return foldr(List::cons, ys, xs);
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

    public static<A> List<A> reverse(List<A> xs) {
        return foldl((ys,y) -> cons(y,ys), empty(), xs);
    }

    public static<A> boolean contains(A x, List<A> xs) {
        if (xs.isEmpty()) {
            return false;
        } else {
            return x.equals(xs.head()) ? true : contains(x, xs.tail());
        }
    }

    public static<A> List<A> flatten(List<List<A>> xss) {
        return foldr(ListsPrime::extend, empty(), xss);
    }

    public static<A> boolean isSubList(List<A> xs, List<A> ys) {
        if (xs.isEmpty()) {
            return true;
        } else if (ys.isEmpty()) {
            return false;
        } else {
            return xs.head().equals(ys.head()) ? isSubList(xs.tail(), ys.tail()) : isSubList(xs, ys.tail());
        }
    }

    public static<A> boolean isPrefix(List<A> xs, List<A> ys) {
        if (xs.isEmpty()) {
            return true;
        } else if (ys.isEmpty()) {
            return false;
        } else {
            return xs.head().equals(ys.head()) && isPrefix(xs.tail(), ys.tail());
        }
    }

    public static<A,B> List<B> flatMap(Function<A,List<B>> f, List<A> xs) {
        return flatten(map(f, xs));
    }

    public static<A> List<List<A>> powerSet(List<A> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else {
            List<List<A>> prev = powerSet(xs.tail());
            return extend(prev, map(y -> cons(xs.head(), y), prev));
        }
    }

    public static<A> List<A> take(int n, List<A> xs) {
        if (n == 0 || xs.isEmpty()) {
            return empty();
        } else {
            return cons(xs.head(), take(n-1, xs.tail()));
        }
    }

    public static<A> List<A> drop(int n, List<A> xs) {
        if (n == 0) {
            return xs;
        } else if (xs.isEmpty()) {
            return empty();
        } else {
            return drop(n-1, xs.tail());
        }
    }

    public static<A> List<A> takeWhile(Predicate<A> p, List<A> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else {
            return p.test(xs.head()) ? cons(xs.head(), takeWhile(p, xs.tail())) : empty();
        }
    }

    public static<A> List<A> dropWhile(Predicate<A> p, List<A> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else {
            return p.test(xs.head()) ? dropWhile(p, xs.tail()) : xs;
        }
    }

    public static<A> Function<A,A> id() {
        return a -> a;
    }

    public static<A,B,C> Function<A,C> compose(Function<B,C> f, Function<A,B> g) {
        return a -> f.apply(g.apply(a));
    }

    public static Integer sum(List<Integer> xs) {
        return foldr((x,y) -> x+y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return foldr((x,y) -> x*y, 1, xs);
    }

    public static<A> List<A> unique(List<A> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else {
            return contains(xs.head(), xs.tail()) ? unique(xs.tail()) : cons(xs.head(), unique(xs.tail()));
        }
    }

    public static List<Integer> sort(List<Integer> xs) {
        if (xs.isEmpty()) {
            return empty();
        } else {
            List<Integer> less = filter(x -> x < xs.head(), xs.tail());
            List<Integer> more = filter(x -> x >  xs.head(), xs.tail());
            return flatten(list(sort(less), list(xs.head()), sort(more)));
        }
    }

    public static void main(String[] args) {
        List<Integer> ls = list(1);
        ls.iterator().next();

        Function<List<Integer>, Integer> sum = xs -> foldLeft((a,b) -> a+b, 0, xs);
        Function<List<Integer>, Integer> product = xs -> foldLeft((a,b) -> a*b, 1, xs);
        System.out.println(sum.apply(list(1, 2, 3, 4)));
        System.out.println(product.apply(list(1, 2, 3, 4)));

    }
}
