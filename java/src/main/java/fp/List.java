package fp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<T> implements  Iterable<T> {
    private static final List EMPTY = cons(null, null);

    private final T head;
    private final List<T> tail;

    public static<T> List<T> empty() {
        return EMPTY;
    }

    public static<T> List<T> cons(T head, List<T> tail) {
        return new List(head, tail);
    }

    public static<T> List<T> list(T... arr) {
        List<T> root = EMPTY;
        for (int i=arr.length-1; i>=0; i--) {
            root = cons(arr[i], root);
        }
        return root;
    }

    private List(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    public T head() {
        return head;
    }

    public List<T> tail() {
        return tail;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    public String toString() {
        if (this == EMPTY) {
            return "[]";
        } else {
            return String.format("%s:%s", head, tail);
        }
    }

    @Override
    public boolean equals(Object other) {
        return Lists.isSame(this, (List<T>) other);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private List<T> cur = List.this;

            @Override
            public boolean hasNext() {
                return !cur.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("list is empty");
                }
                T res = cur.head();
                cur = cur.tail();
                return res;
            }
        };
    }
}
