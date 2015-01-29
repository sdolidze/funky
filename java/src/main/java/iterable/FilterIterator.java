package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private Predicate<T> predicate;
    private PeekIterator<T> iterator;

    private FilterIterator(Predicate<T> predicate, Iterator<T> iterator) {
        this.predicate = predicate;
        this.iterator = new PeekIterator<>(iterator);
    }

    @Override
    public boolean hasNext() {
        if (!iterator.hasNext()) {
            return false;
        }

        while (iterator.hasNext()) {
            if (predicate.test(iterator.peek())) {
                return true;
            }
            iterator.next();
        }

        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.next();
    }
}