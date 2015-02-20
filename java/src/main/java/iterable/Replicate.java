package iterable;

import java.util.Iterator;

/**
 * Created by sandro on 2/20/15.
 */
public class Replicate<T> implements Iterable<T> {
    private T value;

    public Replicate(T value) {
        this.value = value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return value;
            }
        };
    }
}
