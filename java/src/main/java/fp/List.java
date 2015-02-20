package fp;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * todo: null represents empty list, so remove iterable
 * I'm deliberately breaking encapsulation, not using final properties and using null as empty list.
 * Doing so makes code smaller.
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

    public static<T> List<T> listIter(Iterable<T> it) {
        List<T> newList = cons(null, null);
        List<T> head = newList;

        for (T elem: it) {
            newList.tail = cons(elem, null);
            newList = newList.tail;
        }

        return head.tail;
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
    public boolean equals(Object that) {
       return Prelude.equals(this, (List<T>) that);
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("not yet implemented");
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
