package iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/*
 * Haskell
 * -------
 * takeWhile p [] = []
 * takeWhile p (x:xs) = if p x then x : takeWhile xs else []

 * Python
 * ------
 * def take_while(p, xs):
 *     for x in xs:
 *         if p(x):
 *             yield x
 *         else
 *             return

 * Java
 * ----
 * can't this be made simpler?
 */
public class TakeWhile<T> implements Iterator<T> {
    private Predicate<T> predicate;
    private PeekIterator<T> iterator;
    private boolean predicateFailed;

    public TakeWhile(Predicate<T> predicate, Iterator<T> iterator) {
        this.predicate = predicate;
        this.iterator = new PeekIterator<>(iterator);
        this.predicateFailed = false;
    }

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

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.next();
    }
}
