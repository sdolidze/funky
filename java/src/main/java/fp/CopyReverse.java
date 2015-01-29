package fp;

import static fp.List.cons;
import static fp.List.list;

/**
 * Created by sandro on 1/10/15.
 */
public class CopyReverse {
    private static<T> List<T> snoc(T x, List<T> xs) {
        return xs == null ? list(x) : cons(xs.head, snoc(x, xs.tail));
    }

    public static<T> List<T> reverseRec(List<T> xs) {
        return xs == null ? null : snoc(xs.head, reverseRec(xs.tail));
    }

    private static<T> List<T> reverseTailRec(List<T> newList, List<T> oldList) {
        return oldList == null ? null : reverseTailRec(cons(oldList.head, newList), oldList.tail);
    }

    public static<T> List<T> reverseTailRec(List<T> oldList) {
        return reverseTailRec(null, oldList);
    }

    public static<T> List<T> reverseIter(List<T> oldList) {
        List<T> newList = null;
        for (T x: oldList) {
            newList = cons(x, newList);
        }
        return newList;
    }

    public static<T> List<T> reverseIterInPlace(List<T> oldList) {
        List<T> newList = null;
        while (oldList != null) {
            // remove from old list
            List<T> temp = oldList;
            oldList = oldList.tail;

            // insert into new list
            temp.tail = newList;
            newList = temp;
        }
        return newList;
    }

    private static<T> List<T> reverseTailRecInPlace(List<T> newList, List<T> oldList) {
        if (oldList == null) {
            return newList;
        }
        List<T> temp = oldList.tail;
        oldList.tail = newList;
        return reverseTailRecInPlace(oldList, temp);
    }

    private static<T> List<T> reverseTailRecInPlace(List<T> oldList) {
        return reverseTailRecInPlace(null, oldList);
    }

    public static<T> List<T> reverseStanford(List<T>[] headRef) {
        throw new RuntimeException("not yet implemented");
    }

    public static<T> List<T> copyRec(List<T> oldList) {
        return oldList == null ? null : cons(oldList.head, copyRec(oldList.tail));
    }

    private static<T> void copyTailRec(List<T> newList, List<T> oldList) {
        if (oldList != null) {
            newList.tail = cons(oldList.head, null);
            copyTailRec(newList.tail, oldList.tail);
        }
    }

    public static<T> List<T> copyTailRec(List<T> oldList) {
        List<T> newList = cons(null, null);
        copyTailRec(newList, oldList);
        return newList.tail;
    }

    public static<T> List<T> copyIter(List<T> oldList) {
        List newList = cons(null, null);
        List res = newList;

        while (oldList != null) {
            newList.tail = cons(oldList.head, null);
            newList = newList.tail;
            oldList = oldList.tail;
        }

        return res.tail;
    }

    public static void main(String[] args) {
        List<Integer> xs = list(1,2,3);
        System.out.println(copyRec(xs));
        System.out.println(xs);
    }
}
