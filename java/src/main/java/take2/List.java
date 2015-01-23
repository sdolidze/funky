package take2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * I'm deliberately breaking encapsulation, not using final properties and using null as empty list.
 * Do so makes code smaller.
 * @param <T>
 */
public class List<T> implements  Iterable<T> {
    public T head;
    public List<T> tail;

    public static<T> List<T> cons(T head, List<T> tail) {
        return new List(head, tail);
    }

    public static<T> List<T> list(T... arr) {
        List<T> root = null;
        for (int i=arr.length-1; i>=0; i--) {
            root = cons(arr[i], root);
        }
        return root;
    }

    private List(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", head, tail);
    }

    @Override
    public boolean equals(Object other) {
        List<T> that = (List<T>) other;
        return this.head.equals(that.head) && this.tail.equals(that.tail);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private List<T> cur = List.this;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("empty list");
                }
                T res = cur.head;
                cur = cur.tail;
                return res;
            }
        };
    }
}
