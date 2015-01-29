package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * I think this is too much, Eager version works just fine and is much simpler
 * actually, this can be made even more lazy
 */
public class PeekIteratorLazy<T> implements Iterator<T> {
    private Iterator<T> iterator;
    private T buffer;

    public PeekIteratorLazy(Iterator<T> iterator) {
        this.iterator = iterator;
        this.buffer = null;
    }

    @Override
    public boolean hasNext() {
        return peek() != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T temp = buffer; // buffer is never null here, it is set by hasNext() -> peek()
        buffer = null;
        return temp;
    }

    /**
     * may return null, if rest is empty
     */
    public T peek() {
        if (buffer != null) {
            return buffer;
        }

        if (iterator.hasNext()) {
            buffer = iterator.next();
            return buffer;
        }

        return null;
    }
}
