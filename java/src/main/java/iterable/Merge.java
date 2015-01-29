package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Merges two sorted iterators into also sorted one
 */
public class Merge<T extends Comparable<T>> implements Iterator<T> {
    private PeekIterator<T> left;
    private PeekIterator<T> right;

    public Merge(Iterator<T> left, Iterator<T> right) {
        this.left = new PeekIterator<>(left);
        this.right = new PeekIterator<>(right);
    }

    @Override
    public boolean hasNext() {
        return left.hasNext() || right.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (!left.hasNext()) {
            return right.next();
        }

        if (!right.hasNext()) {
            return left.next();
        }

        if (left.peek().compareTo(right.peek()) < 0) {
            return left.next();
        }

        return right.next();
    }
}
