package fp;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @author Sandro Dolidze <sdolidze3@gmail.com>
 */
public class Fibonacci implements Iterable<Integer> {

    public static <T> Iterator<T> takeWhile(Iterable<T> iterable, Predicate<T> predicate) {
        return new Iterator<T>() {
            private T buffer = null;
            private boolean predicateFailed = false;

            public boolean hasNext() {
                return true;
            }

            public T next() {
                return iterable.iterator().next();
            }
        };
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int cur = 1;
            private int nxt = 1;

            public boolean hasNext() {
                return true;
            }

            public Integer next() {
                int res = cur;
                cur = nxt;
                nxt = res+nxt;
                return res;
            }
        };
    }

    public static void main(String[] args) {
        Iterator<Integer> fibonacci = new Fibonacci().iterator();

        for (int i=0; i<10; i++) {
            System.out.printf("%d ", fibonacci.next());
        }
    }

}
