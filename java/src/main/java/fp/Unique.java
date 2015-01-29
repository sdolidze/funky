package fp;

import static fp.List.cons;

/**
 * Created by sandro on 1/25/15.
 */
public class Unique {
    public static<T> boolean contains(T x, List<T> xs) {
        if (xs == null) {
            return false;
        } else {
            return x.equals(xs.head) ? true : contains(x, xs.tail);
        }
    }

    public static<T> boolean containsIter(T y, List<T> xs) {
        for (T x: xs) {
            if (x.equals(y)) {
                return true;
            }
        }
        return false;
    }

    public static<T> List<T> remove(T x, List<T> xs) {
        if (xs == null) {
            return null;
        } else {
            return x.equals(xs.head) ? remove(x, xs.tail) : cons(xs.head, remove(x, xs.tail));
        }
    }

    public static<T> List<T> removeInPlace(T x, List<T> xs) {
        if (xs == null) {
            return null;
        } else if (x.equals(xs.head)) {
            return removeInPlace(x, xs.tail);
        } else  {
            xs.tail = removeInPlace(x, xs.tail);
            return xs;
        }
    }

    public static<T> List<T> removeInPlaceIter(T x, List<T> oldList) {
        List<T> newList = cons(null, null);
        List<T> head = newList;

        while (oldList != null) {
            if (!x.equals(oldList.head)) {
                newList.tail = oldList;
                newList = newList.tail;
            }
            oldList = oldList.tail;
        }

        newList.tail = null;
        return head.tail;
    }

    private static<T> void removeTailRecInPlace(T x, List<T> newList, List<T> oldList) {
        if (oldList == null) {
            newList.tail = null;
            return;
        }

        if (!x.equals(oldList.head)) {
            newList.tail = oldList;
            removeTailRecInPlace(x, newList.tail, oldList.tail);
        } else {
            removeTailRecInPlace(x, newList, oldList.tail);
        }
    }

    public static<T> List<T> removeRecInPlace(T x, List<T> oldList) {
        List<T> newList = cons(null, null);
        removeTailRecInPlace(x, newList, oldList);
        return newList.tail;
    }

    public static<T> List<T> unique(List<T> xs) {
        // maintains last element order
        if (xs == null) {
            return null;
        } else {
            return contains(xs.head, xs.tail) ? unique(xs.tail) : cons(xs.head, unique(xs.tail));
        }
    }

    public static<T> List<T> uniqueInPlace(List<T> xs) {
        // maintains last element order
        if (xs == null) {
            return null;
        } else if (contains(xs.head, xs.tail)) {
            return uniqueInPlace(xs.tail);
        } else {
            xs.tail = uniqueInPlace(xs.tail);
            return xs;
        }
    }

    public static<T> List<T> uniqueIterInPlace(List<T> xs) {
        // maintains first element order
        List<T> head = xs;

        while (xs != null) {
            xs.tail = remove(xs.head, xs.tail);
            xs = xs.tail;
        }

        return head;
    }

    private static<T> void uniqueTailRecInPlaceP(List<T> oldList) {
        if (oldList != null) {
            oldList.tail = remove(oldList.head, oldList.tail);
            uniqueTailRecInPlaceP(oldList.tail);
        }
    }

    public static<T> List<T> uniqueTailRecInPlace(List<T> xs) {
        uniqueTailRecInPlaceP(xs);
        return xs;
    }
}
