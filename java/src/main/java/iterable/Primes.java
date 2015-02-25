package iterable;

import java.util.Iterator;

/**
 * Created by sandro on 2/25/15.
 */
public class Primes implements Iterable<Integer> {
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };
    }
}
