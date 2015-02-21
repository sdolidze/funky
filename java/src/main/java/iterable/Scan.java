package iterable;

import fp.Integers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * Created by sandro on 2/21/15.
 */
public class Scan {
    // todo: can I simplify this?
    public static<A, B> Iterable<A> scanLeft(BiFunction<A, B, A> f, A v, Iterable<B> it) {
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

    public static void main(String[] args) {
        Iterable<Integer> xs = scanLeft(Integers::add, 0, Arrays.asList());
        xs.forEach(System.out::println);
    }

}
