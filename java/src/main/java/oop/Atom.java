package oop;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sandro on 1/4/15.
 */
public class Atom<T> {
    private volatile T value;

    public Atom(T value) {
        this.value = value;
    }

    public synchronized void swap(Function<T, T> f) {
        this.value = f.apply(value);
    }

    public synchronized boolean cas(Predicate<T> p, Function<T,T> f) {
        if (!p.test(value)) {
            return false;
        }
        swap(f);
        return true;
    }

    public synchronized void reset(T value) {
        this.value = value;
    }

    public synchronized T get() {
        return value;
    }
}
