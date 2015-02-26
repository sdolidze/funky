package iterable;

import fp.Integers;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sandro on 2/25/15.
 */
public class Iterables {
    public static Iterable<Integer> primes() {
        return filter(Integers::isPrime, integers());
    }

    public static Iterable<Integer> integers() {
        return iterate(Integers::inc, 0);
    }

    public static<A> Iterable<A> iterate(Function<A, A> f, A seed) {
        return () -> new Iterator<A>() {
            private A acc = seed;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                A cur = acc;
                acc = f.apply(acc);
                return cur;
            }
        };
    }

    public static<A> Iterable<A> filter(Predicate<A> p, Iterable<A> it) {
        PeekIterator<A> pit = new PeekIterator<>(it.iterator());
        return () -> new Iterator<A>() {
            @Override
            public boolean hasNext() {
                if (!pit.hasNext()) {
                    return false;
                }

                while (pit.hasNext()) {
                    if (p.test(pit.peek())) {
                        return true;
                    }
                    pit.next();
                }

                return false;
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return pit.next();
            }
        };
    }

    public static<A> Iterable<A> take(int n, Iterable<A> it) {
        Iterator<A> iterator = it.iterator();
        return () -> new Iterator<A>() {
            private int leftToGive = n;

            @Override
            public boolean hasNext() {
                return leftToGive > 0;
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                leftToGive--;
                return iterator.next();
            }
        };
    }

    public static<A> Iterable<A> takeWhile(Predicate<A> predicate, Iterable<A> iterable) {
        return () -> new Iterator<A>() {
            private PeekIterator<A> iterator = new PeekIterator<>(iterable.iterator());
            private boolean predicateFailed = false;

            public boolean hasNext() {
                if (predicateFailed || !iterator.hasNext()) {
                    return false;
                }

                if (!predicate.test(iterator.peek())) {
                    predicateFailed = true;
                    return false;
                }

                return true;
            }

            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return iterator.next();
            }
        };
    }

    public static<A, B> Iterable<A> scan(BiFunction<A, B, A> f, A v, Iterable<B> it) {
        return () -> new Iterator<A>() {
            private A acc = v;
            private boolean isFirst = true;
            private Iterator<B> xs = it.iterator();

            @Override
            public boolean hasNext() {
                return isFirst || xs.hasNext();
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (isFirst) {
                    isFirst = false;
                    return acc;
                }
                acc = f.apply(acc, xs.next());
                return acc;
            }
        };
    }

    public static<A> Iterable<A> replicate(A v) {
        return () -> new Iterator<A>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                return v;
            }
        };
    }

    public static<A> Iterable<A> repeat(int n, A v) {
        return () -> new Iterator<A>() {
            private int leftToGive = n;

            @Override
            public boolean hasNext() {
                return leftToGive > 0;
            }

            @Override
            public A next() {
                leftToGive--;
                return v;
            }
        };
    }

    public static<A> Iterable<A> cycle(Iterable<A> it) {
        // iterable must be finite, should fit in memory, should return fast
        return () -> new Iterator<A>() {
            private List<A> buffer = toList(it);
            private int pos = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public A next() {
                A cur = buffer.get(pos);
                pos++;
                pos %= buffer.size();
                return cur;
            }
        };
    }

    public static<A,B> Iterable<B> map(Function<A,B> f, Iterable<A> xs) {
        return () -> new Iterator<B>() {
            private Iterator<A> it = xs.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public B next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return f.apply(it.next());
            }
        };
    }

    public static<A> Iterable<A> extend(Iterable<A> xs, Iterable<A> ys) {
        return () -> new Iterator<A>() {
            private Iterator<A> xit = xs.iterator();
            private Iterator<A> yit = ys.iterator();

            @Override
            public boolean hasNext() {
                return xit.hasNext() || yit.hasNext();
            }

            @Override
            public A next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (xit.hasNext()) {
                    return xit.next();
                } else /* yit.hasNext() */ {
                    return yit.next();
                }
            }
        };
    }

    public static<T> List<T> toList(Iterable<T> it) {
        List<T> xs = new ArrayList<>();
        for (T x: it) {
            xs.add(x);
        }
        return xs;
    }

    public static<T> List<T> list(T... xs) {
        // careful: result is immutable list
        return Arrays.asList(xs);
    }

    public static void main(String[] args) {
        Iterable<String> xs = repeat(10, "sandro");
        xs.forEach(System.out::println);
    }
}
