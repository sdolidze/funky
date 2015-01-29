package fp;

import static fp.List.cons;
import static fp.Prelude.*;

/**
 * join two sorted lists into one sorted
 */
public class Merge {
    public static<T extends Comparable<T>> List<T> mergeRec(List<T> xs, List<T> ys) {
        if (xs == null) {
            return ys;
        } else if (ys == null) {
            return xs;
        } else if (xs.head.compareTo(ys.head) < 0) {
            return cons(xs.head, mergeRec(xs.tail, ys));
        } else {
            return cons(ys.head, mergeRec(xs, ys.tail));
        }
    }

    /**
     * result will be in reverse order
     */
    public static<T extends Comparable<T>> List<T> mergeTailRec(List<T> acc, List<T> xs, List<T> ys) {
        if (xs == null) {
            return push(ys, acc);
        } else if (ys == null) {
            return push(xs, acc);
        } else if (xs.head.compareTo(ys.head) < 0) {
            return mergeTailRec(cons(xs.head, acc), xs.tail, ys);
        } else {
            return mergeTailRec(cons(ys.head, acc), xs, ys.tail);
        }
    }

    public static<T extends Comparable<T>> List<T> mergeTailRec(List<T> xs, List<T> ys) {
        return reverse(mergeTailRec(null, xs, ys));
    }

    public static<T extends Comparable<T>> List<T> mergeIter(List<T> xs, List<T> ys) {
        List<T> newList = cons(null, null);
        List<T> head = newList;

        while (xs != null && ys != null) {
            if (xs.head.compareTo(ys.head) < 0) {
                newList.tail = cons(xs.head, null);
                xs = xs.tail;
            } else {
                newList.tail = cons(ys.head, null);
                ys = ys.tail;
            }
            newList = newList.tail;
        }

        if (xs != null) {
            newList.tail = xs;
        }

        if (ys != null) {
            newList.tail = ys;
        }

        return head.tail;
    }


    public static<T extends Comparable<T>> List<T> mergeRecInPlace(List<T> xs, List<T> ys) {
        if (xs == null) {
            return ys;
        } else if (ys == null) {
            return xs;
        } else if (xs.head.compareTo(ys.head) < 0) {
            xs.tail = mergeRecInPlace(xs.tail, ys);
            return xs;
        } else {
            ys.tail = mergeRecInPlace(xs, ys.tail);
            return ys;
        }
    }

    public static<T extends Comparable<T>> List<T> mergeIterInPlace(List<T> xs, List<T> ys) {
        List<T> newList = cons(null, null);
        List<T> head = newList;

        while (xs != null && ys != null) {
            if (xs.head.compareTo(ys.head) < 0) {
                newList.tail = xs;
                xs = xs.tail;
            } else {
                newList.tail = ys;
                ys = ys.tail;
            }
            newList = newList.tail;
        }

        if (xs != null) {
            newList.tail = xs;
        }

        if (ys != null) {
            newList.tail = ys;
        }

        return head.tail;
    }

    public static<T extends Comparable<T>> List<T> mergeTailRecInPlace(List<T> newList, List<T> xs, List<T> ys) {
        if (xs == null) {
            newList.tail = ys;
            return newList;
        } else if (ys == null) {
            newList.tail = xs;
            return newList;
        } else if (xs.head.compareTo(ys.head) < 0) {
            newList.tail = xs;
            return mergeTailRecInPlace(newList.tail, xs.tail, ys);
        } else {
            newList.tail = ys;
            return mergeTailRecInPlace(newList.tail, xs, ys.tail);
        }
    }

    public static<T extends Comparable<T>> List<T> mergeTailRecInPlace(List<T> xs, List<T> ys) {
        List<T> newList = cons(null, null);
        mergeTailRecInPlace(newList, xs, ys);
        return newList.tail;
    }
}
