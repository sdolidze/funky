package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Original idea by Giorgi Gogiashvili
 */
public class PeekIterator<T> implements Iterator<T> {
    private T first;
    private Iterator<T> rest;

    public PeekIterator(Iterator<T> iterator) {
        // invariant: when first is null iterator is empty
        this.first = iterator.hasNext() ? iterator.next() : null;
        this.rest = iterator;
    }

    @Override
    public boolean hasNext() {
        return first != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T temp = first;
        first = rest.hasNext() ? rest.next() : null;
        return temp;
    }

    /**
     * may return null, if rest is empty
     */
    public T peek() {
        return first;
    }
}
