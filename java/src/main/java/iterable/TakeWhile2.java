package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class TakeWhile2<T> implements Iterator<T> {
    private Predicate<T> predicate;
    private Iterator<T> iterator;
    private T buffer = null;
    private boolean predicateFailed;

    public TakeWhile2(Predicate<T> predicate, Iterator<T> iterator) {
        this.predicate = predicate;
        this.iterator = new PeekIterator<>(iterator);
        this.buffer = null;
        this.predicateFailed = false;
    }

    public boolean hasNext() {
        if (buffer != null) {
            return true;
        }

        if (predicateFailed || !iterator.hasNext()) {
            return false;
        }

        T temp = iterator.next();
        if (!predicate.test(temp)) {
            predicateFailed = true;
            return false;
        }

        buffer = temp;
        return true;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T temp = buffer; // buffer is not null, because hasNext() sets it
        buffer = null;
        return temp;
    }
}
